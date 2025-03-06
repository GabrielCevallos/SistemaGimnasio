import requests
from routes.backend_urls import USUARIO_FIND_ROLE_URL
from routes.utils.classes import Usuario
from routes.utils.classes import AuthUser 

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