from .router import *
from .utils.auth_utils import *

SCP_URL = f'{BASE_URL}/suscripcion' 

#UPDATE
@router.route('/suscripcion/update/<id>')
@login_required(roles = ['ADMINISTRADOR'])
def suscripcion_update(id: int, headers: dict, auth_user: AuthUser) -> str:
    headers['Content-Type'] = 'application/json'
    suscripcion = requests.get(f'{SCP_URL}/get/{id}', headers=headers).json()['data']
    tipo_suscripcion_list = requests.get(f'{SCP_URL}/tipoSuscripcion',headers=headers).json()['data']
    return render_template(
        template_name_or_list='fragmento/suscripcion/update.html',
        suscripcion=suscripcion,
        tipo_suscripcion=tipo_suscripcion_list,
        auth_user=auth_user,
    )

@router.route('/suscripcion/update/send/', methods=['POST'])
@login_required(roles = ['ADMINISTRADOR'])
def update_suscripcion_send(headers,usr):
    headers["Content-Type"] = "application/json"
    form = request.form

    dataF = {
        "id": form['idS'],
        "fechaInicio": form['nameFechaI'],
        "tipo": form['nameTipo'],
    }

    r = requests.post("http://localhost:8080/api/suscripcion/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='success')
        return redirect('/my_profile' if (eval(form['my-profile'])) else f'/view_user/{form["id"]}/{eval(form["admins"])}')
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect('/my_profile' if (eval(form['my-profile'])) else f'/view_user/{form["id"]}/{eval(form["admins"])}')
    

