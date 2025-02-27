BASE_URL = 'http://localhost:8080'
PERSONA_URL = f'{BASE_URL}/api/persona'
PERFIL_URL = f'{BASE_URL}/api/perfil'
USUARIO_URL = f'{BASE_URL}/api/usuario'
ESTADISTICA_URL = f'{BASE_URL}/api/estadistica'
SUSCRIPCION_URL = f'{BASE_URL}/api/suscripcion'

PERSONA_ENUM_URL = f'{PERSONA_URL}/enumerations'

AUTH_URL = f'{BASE_URL}/auth'
# true para que se cree un usuario unicamente con la información de la persona
REGISTER_URL = f'{AUTH_URL}/register/true'
LOGIN_URL = f'{AUTH_URL}/login'
LOGOUT_URL = f'{AUTH_URL}/logout'
REFRESH_URL = f'{AUTH_URL}/refresh'

UPDATE_PERSONA_URL = f'{PERSONA_URL}/update'
UPDATE_PERFIL_URL = f'{PERFIL_URL}/update'
UPDATE_USUARIO_URL = f'{USUARIO_URL}/update'
UPDATE_SUSCRIPCION_URL = f'{SUSCRIPCION_URL}/update'
CURRENT_AUTH_USER_URL = f'{AUTH_URL}/user-authenticated'

CHANGE_PASSWORD_URL = f'{USUARIO_URL}/change-password'

USUARIO_FIND_URL = f'{USUARIO_URL}/find'
USUARIO_FIND_ROLE_URL = f'{USUARIO_FIND_URL}/role'
USUARIO_FIND_ID_URL = f'{USUARIO_FIND_URL}/id'

PERSONA_FIND_URL = f'{PERSONA_URL}/find'
PERSONA_FIND_ID_URL = f'{PERSONA_FIND_URL}/id'

SUSCRIPCION_FIND_URL = f'{SUSCRIPCION_URL}/find'
SUSCRIPCION_FIND_PERSONA_URL = f'{SUSCRIPCION_FIND_URL}/persona' # /{numero_documento}

