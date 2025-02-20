import os
import requests
from werkzeug.utils import secure_filename
from flask import render_template, request, flash, redirect, Response
from .router import *
from .utils.decorator import *
from .utils.classes import *
from .utils.templates_url import users_urls as templates

UPLOAD_FOLDER = 'static/img/user_profile/'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif', 'webp'}

P_URL = f'{BASE_URL}/api/usuario'

def parse_personas(data: list) -> list:
    return [Persona.from_dict(item) for item in data]

def assign_persona_numbers(personas: list) -> list:
    for i, persona in enumerate(personas, 1):
        persona.numero = i
    return personas

def parse_persona_numbers(data: list) -> list:
    personas = parse_personas(data)
    return assign_persona_numbers(personas)

def get_persona_list(role: str, headers: dict, auth_user: AuthUser) -> list:
    role = 'CLIENTE' if role == 'client' else 'ADMINISTRADOR'
    response = requests.get(f'{P_URL}/find/role/{role}', headers=headers)
    persona_list = response.json()['data']
    # Remove the authenticated user from the list
    return [persona for persona in persona_list if persona['usuarioId'] != auth_user.id]

@router.route('/users/<role>/list')
@login_required(roles=['ADMINISTRADOR'])
def users_client_list(headers: dict, auth_user: AuthUser, role: str) -> str:
    persona_list = get_persona_list(role, headers, auth_user)
    personas = parse_persona_numbers(persona_list)
    is_admin_list_view = True if role == 'admin' else False

    return render_template(
        template_name_or_list = templates['list'],
        personas = personas,
        auth_user = auth_user,
        admin = is_admin_list_view
    )

@router.route('/register/user/<role>')
@login_required(roles=['ADMINISTRADOR'])
def register_user(headers: dict, auth_user: AuthUser, role: str) -> str:
    e = requests.get(f'{BASE_URL}/api/persona/enumerations',headers=headers).json()['data']
    role = 'ADMINISTRADOR' if role == 'admin' else 'CLIENTE'
    return render_template(templates['register'], e=e, auth_user=auth_user, role=role)

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
    response = requests.post(f'{BASE_URL}/auth/register/true',headers=headers,json=json)
    if response.status_code == 200:
        flash('Se ha registrado el usuario',category='success')
    else:
        flash('No se ha podido registrar el usuario',category='error')
    return redirect('/users/admin/list' if data['rol'] == 'ADMINISTRADOR' else '/users/client/list')

def allowed_file(filename: str) -> bool:
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def delete_unreferenced_images(headers: dict) -> str:
    personas = requests.get(f'{BASE_URL}/api/perfil', headers=headers).json()['data']
    referenced_images = {persona['imagen'] for persona in personas if 'imagen' in persona}

    referenced_images.add('user.png')

    stored_images = set(os.listdir(UPLOAD_FOLDER))

    unreferenced_images = stored_images - referenced_images

    for image in unreferenced_images:
        os.remove(os.path.join(UPLOAD_FOLDER, image))

    return f"Deleted {len(unreferenced_images)} unreferenced images."

@router.route('/user/update/send',methods=['POST'])
@login_required()
def perfil_update_send(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    if 'image' in request.files:
        file = request.files['image']

        if file.name == '':
            flash('No se ha seleccionado un archivo')
            return redirect('/users/list')

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(UPLOAD_FOLDER, filename)) 
            data['imagen'] = file.filename

    if not 'imagen' in data:
        data['imagen'] = data['current-image']                                                 
    response = requests.post(f'{BASE_URL}/perfil/update',headers=headers,json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}',category='success' if ok else 'error')
    print(delete_unreferenced_images(headers=headers))
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data["userId"]}/{eval(data["admins"])}') 

@router.route('/change_password',methods=['POST'])
@login_required()
def change_password(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    response = requests.post(f'{BASE_URL}/auth/change/password',headers=headers,json=data)
    ok = response.status_code == 200
    print(data)
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado la contraseña" if ok else "no se ha podido actualizar la contraseña"}',category='success' if ok else 'error')
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data["id"]}/{eval(data["admins"])}')

@router.route('/user/update/persona',methods=['POST'])
@login_required()
def update_persona_send(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    response = requests.patch(P_URL,headers=headers,json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}',category='success' if ok else 'error')
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data["userId"]}/{eval(data["admins"])}')

@router.route('/view_user/<id>/<admins>')
@login_required(roles=['ADMINISTRADOR'])
def persona_view(headers: dict, auth_user: AuthUser, id: int, admins: str) -> str:
    response = requests.get(f'{BASE_URL}/api/usuario/find/id/{id}', headers=headers)

    persona_perfil = PerfilPersona.from_dict(response.json()['data'])

    response = requests.get(
        f'{BASE_URL}/api/suscripcion/find/persona/{persona_perfil.numero_documento}',
        headers=headers
    )

    suscripcion = Suscripcion.from_dict(response.json()['data'])

    perfil = PerfilSuscripcion.from_suscripcion_perfil(persona_perfil, suscripcion)
    response = requests.get(f'{BASE_URL}/api/persona/enumerations', headers=headers)

    enums = response.json()['data']

    return render_template(
        'fragmento/users_view/user/view_user.html',
        enums=enums,
        auth_user=auth_user,
        perfil=perfil,
        admins=eval(admins),
        my_profile=False
    ) 

@router.route('/my_profile')
@login_required()
def my_profile(headers: dict, auth_user: AuthUser) -> str:
    persona = requests.get(f'{P_URL}/get/{auth_user.id}',headers=headers).json()['data']
    cuenta = requests.get(f'{BASE_URL}/cuenta/search/personaId/{persona["id"]}',headers=headers).json()['data'][0]
    perfil = requests.get(f'{BASE_URL}/perfil/get/{cuenta["perfilId"]}',headers=headers).json()['data']
    estadistica = requests.get(f'{BASE_URL}/estadistica/get/{cuenta["perfilId"]}',headers=headers).json()['data']
    suscripcion = requests.get(f'{BASE_URL}/suscripcion/get/{cuenta["personaId"]}',headers=headers).json()['data']

    enums = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']

    full_user_info = {'persona': persona, 'cuenta': cuenta, 'perfil': perfil, 'estadistica': estadistica, 'suscripcion' : suscripcion, 'my_profile': True }

    return render_template('fragmento/users_view/user/view_user.html', auth_user=auth_user, full_user_info=full_user_info, enums=enums ,my_profile=True)

@router.route('/user/delete/<id>/<admins>')
@login_required(roles=['ADMINISTRADOR'])
def persona_delete(id: int, headers: dict, auth_user: AuthUser, admins: bool) -> str:
    return render_template('fragmento/users_view/delete.html', id=id, auth_user=auth_user, admins=eval(admins))

@router.route('/user/delete/send',methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def delete_user(headers: dict, auth_user: AuthUser) -> Response:
    data = request.form.to_dict()
    response = requests.delete(f'{BASE_URL}/auth/delete/{data["id"]}',headers=headers)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha eliminado el registro" if ok else "no se ha podido eliminar el registro"}',category='success' if ok else 'error')
    return redirect('/users/admin/list' if eval(data['admins']) else '/users/client/list')
