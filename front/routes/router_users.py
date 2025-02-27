import os
from werkzeug.utils import secure_filename
from flask import render_template, request, flash, redirect, Response, url_for
import requests 
from routes.router import router
from routes.utils.auth_utils import login_required
from routes.backend_urls import (
    BASE_URL, PERSONA_URL, USUARIO_URL, USUARIO_FIND_ROLE_URL, 
    PERSONA_ENUM_URL, REGISTER_URL, PERFIL_URL, UPDATE_PERFIL_URL,
    UPDATE_PERSONA_URL, USUARIO_FIND_ID_URL, SUSCRIPCION_FIND_PERSONA_URL,
    CURRENT_AUTH_USER_URL
)
from routes.utils.classes import AuthUser, Perfil, Suscripcion, Persona, Usuario
from routes.utils.templates_url import users_urls as templates

UPLOAD_FOLDER = 'static/img/user_profile/'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif', 'webp'}

#TODO refactorizar, poner el decorator en un directorio 'auth'
#TODO: EN EL BACKEND PONER SUSCRIPCION.NOMBRE COMO SUSCRIPCION.TIPO 
#TODO: PONER EL MANEJO DE IMAGENES, LAS FUNCIONES DE PARSEO Y ASIGNACION DE NUMEROS EN Un modulo aparte

# This function parses a list of dictionaries into a list of Persona objects
def parse_usuario(data: list) -> list:
    return [Usuario.from_dict(item) for item in data]

def assign_usuario_numbers(usuario_list: list) -> list:
    for i, usuario in enumerate(usuario_list, 1):
        usuario.numero = i
    return usuario_list

def parse_persona_numbers(usuario_list: list) -> list:
    usuario_list = parse_usuario(usuario_list)
    return assign_usuario_numbers(usuario_list=usuario_list)

def get_usuario_list(role: str, headers: dict, auth_user: AuthUser) -> list:
    response = requests.get(f'{USUARIO_FIND_ROLE_URL}/{role}', headers=headers)
    usuario_list = response.json()['data']
    # Remove the authenticated user from the list
    return [usuario for usuario in usuario_list if usuario['usuarioId'] != auth_user.usuario_id]

@router.route('/users/<role>/list')
@login_required(roles=['ADMINISTRADOR'])
def users_client_list(headers: dict, auth_user: AuthUser, role: str) -> str:
    usuario_list = get_usuario_list(role, headers, auth_user)
    usuario_list = parse_persona_numbers(usuario_list=usuario_list)
    is_admin_list_view = (role == 'administrador')

    register_url = url_for('router.register_user', role=role)

    template_details = {
        'admin_nav_class': 'active' if is_admin_list_view else '',
        'client_nav_class': 'active' if not is_admin_list_view else '',
        'register_url': register_url,
        'card_title': 'ADMINISTRADORES' if is_admin_list_view else 'CLIENTES',
        'role': role
    }

    return render_template(
        template_name_or_list=templates['list'],
        personas=usuario_list,
        auth_user=auth_user,
        admin=is_admin_list_view,
        template_details=template_details
    )

@router.route('/register/user/<role>')
@login_required(roles=['ADMINISTRADOR'])
def register_user(headers: dict, auth_user: AuthUser, role: str) -> str:
    e = requests.get(PERSONA_ENUM_URL, headers=headers).json()['data']
    return render_template(templates['register'], e=e, auth_user=auth_user, role=role.upper())

@router.route('/register/user/send',methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def register_user_send(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    json = {
        'persona': {
            'nombre': data['nombre'],
            'apellido': data['apellido'],
            'direccion': data['direccion'],
            'fechaNacimiento': data['fechaNacimiento'],
            'telefonoCelular': data['telefonoCelular'],
            'tipoDocumento': data['tipoDocumento'],
            'numeroDocumento': data['numeroDocumento'],
            'genero': data['genero'],
        },
        'rol': {
            'nombre': data['rol']
        },
        'correoElectronico': data['correoElectronico'],
        'contrasena': data['contrasena']
    }   
    # true is for creating a user with new perfil and suscripcion
    response = requests.post(REGISTER_URL, headers=headers, json=json)
    if response.status_code == 200:
        flash('Se ha registrado el usuario',category='success')
    else:
        flash('No se ha podido registrar el usuario',category='error')

    redirection = url_for('router.users_client_list', role=data['rol'].lower())
    return redirect(redirection)

def allowed_file(filename: str) -> bool:
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def delete_unreferenced_images(headers: dict) -> str:
    personas = requests.get(PERFIL_URL, headers=headers).json()['data']
    referenced_images = {persona['imagen'] for persona in personas if 'imagen' in persona}

    referenced_images.add('user.png')

    stored_images = set(os.listdir(UPLOAD_FOLDER))

    unreferenced_images = stored_images - referenced_images

    for image in unreferenced_images:
        os.remove(os.path.join(UPLOAD_FOLDER, image))

    return f"Deleted {len(unreferenced_images)} unreferenced images."

@router.route('/user/profile/update/send', methods=['POST'])
@login_required()
def perfil_update_send(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    if 'image' in request.files:
        file = request.files['image']

        if file.name == '':
            flash('No se ha seleccionado un archivo')
            return redirect(url_for('router.my_profile'))

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(UPLOAD_FOLDER, filename)) 
            data['imagen'] = file.filename

    if not 'imagen' in data:
        data['imagen'] = data['current-image']                                                 
    response = requests.post(UPDATE_PERFIL_URL, headers=headers, json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}',category='success' if ok else 'error')
    print(delete_unreferenced_images(headers=headers))
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data["userId"]}/{eval(data["admins"])}') 

@router.route('/change-password',methods=['POST'])
@login_required()
def change_password(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    response = requests.post(f'{BASE_URL}/auth/change-password',headers=headers,json=data)
    ok = response.status_code == 200
    print(data)
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado la contraseña" if ok else "no se ha podido actualizar la contraseña"}',category='success' if ok else 'error')
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data["id"]}/{eval(data["admins"])}')

@router.route('/user/update/persona',methods=['POST'])
@login_required()
def update_persona_send(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    response = requests.patch(UPDATE_PERSONA_URL, headers=headers, json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}',category='success' if ok else 'error')
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data["userId"]}/{eval(data["admins"])}')

@router.route('/info/user/<id>')
@login_required()
def info_user(headers: dict, auth_user: AuthUser, id: int) -> str:
    if id != auth_user.usuario_id and not auth_user.rol == 'ADMINISTRADOR':
        return render_template('404.html'), 404

    FIND_USER_URL = f'{USUARIO_FIND_ID_URL}/{id}'
    response = requests.get(FIND_USER_URL , headers=headers)
    USER = Usuario.from_dict(response.json()['data'])

    FIND_SUSCRIPCION_URL = f'{SUSCRIPCION_FIND_PERSONA_URL}/{USER.numero_documento}'
    response = requests.get(FIND_SUSCRIPCION_URL, headers=headers)
    suscripcion = Suscripcion.from_dict(response.json()['data'])

    enums = requests.get(PERSONA_ENUM_URL, headers=headers).json()['data']

    is_me = USER.usuario_id == auth_user.usuario_id
    is_admin = USER.rol == 'ADMINISTRADOR'

    template_details = {
        'admin_nav_class': 'active' if is_admin and not is_me  else '',
        'client_nav_class': 'active' if not is_admin and not is_me else '',
    }

    return render_template(
        template_name_or_list='fragmento/users/user/info-user.html',
        enums=enums,
        auth_user=auth_user,
        user=USER,
        suscripcion=suscripcion,
        template_details=template_details
    )

@router.route('/user/delete/<id>')
@login_required(roles=['ADMINISTRADOR'])
def delete_user(id: int, headers: dict, auth_user: AuthUser) -> str:
    return render_template(
        'fragmento/users/delete.html',
        id=id,
        auth_user=auth_user
    )

@router.route('/user/delete/send',methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def delete_user_send(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    response = requests.delete(f'{BASE_URL}/auth/delete/{data["id"]}',headers=headers)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha eliminado el registro" if ok else "no se ha podido eliminar el registro"}',category='success' if ok else 'error')
    return redirect('/users/admin/list' if eval(data['admins']) else '/users/client/list')
