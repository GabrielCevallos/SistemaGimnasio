import os
import requests
from routes.backend_urls import PERFIL_URL

UPLOAD_FOLDER = 'static/img/user_profile/'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif', 'webp'}

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
