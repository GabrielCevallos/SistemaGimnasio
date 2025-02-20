import requests
import json
import time

REGISTER_URL = 'http://localhost:8080/auth/register/true'
LOGIN_URL = 'http://localhost:8080/auth/login'
USERS_LIST_URL = 'http://localhost:8080/api/usuario/find/role/ADMINISTRADOR'

#ruta absoluta al archivo json
json_file = '/home/pccdva/vsc-workspace/users.json'

#leer un archivo con un array de objetos json
def read_json_file(file):
    with open(file) as f:
        return json.load(f)

users = read_json_file(json_file)    
 
for i in range(0,len(users)):
    user = users[i]
    response = requests.post(f'{REGISTER_URL}',json=user)
    #time.sleep(1)

    try:
        print(f'RESPUESTA {i+1} - {str(response)}')
        print(response.json())
    except:
        print(response.text)
        print(response.status_code)


""" user = {
    'correoElectronico':'andrea.morales@email.com',
    'contrasena': 'contrasena123'
}
        
response = requests.post(f'{LOGIN_URL}',json=user)
token = response.json()['accessToken'] 
headers = {'Authorization' : f'Bearer {token}'}

response = requests.get(f'{USERS_LIST_URL}',headers=headers)

try:
    print(response)
    json_response = response.json()
    print(json.dumps(json_response, indent=4, ensure_ascii=False))
except:
    print(response.text)
    print(response.status_code) """