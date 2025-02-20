# CAPA ANTI-CORRUPCIÓN
from dataclasses import dataclass
from datetime import datetime

@dataclass
class Persona:
    persona_id: int
    numero: int
    nombre: str
    telefono_celular: str
    apellido: str
    direccion: str
    fecha_nacimiento: str
    edad: int
    tipo_documento: str
    numero_documento: str
    genero: str

    @staticmethod
    def calcular_edad(fecha_nacimiento:str):
        fecha_nacimiento = datetime.strptime(fecha_nacimiento, '%Y-%m-%d')
        fecha_actual = datetime.now()
        edad = fecha_actual.year - fecha_nacimiento.year
        if fecha_actual.month < fecha_nacimiento.month or (fecha_actual.month == fecha_nacimiento.month and fecha_actual.day < fecha_nacimiento.day):
            edad -= 1
        return edad 

    @staticmethod
    def from_dict(data:dict):
        return Persona(
            persona_id=data['persona']['personaId'],
            telefono_celular=data['persona']['telefonoCelular'],
            numero=None,
            nombre=data['persona']['nombre'],
            apellido=data['persona']['apellido'],
            direccion=data['persona']['direccion'],
            fecha_nacimiento=data['persona']['fechaNacimiento'],
            edad=Persona.calcular_edad(data['persona']['fechaNacimiento']),
            tipo_documento=data['persona']['tipoDocumento'],
            numero_documento=data['persona']['numeroDocumento'],
            genero=data['persona']['genero']
        )

@dataclass
class AuthUser(Persona):
    id: int
    correo: str
    nickname: str
    imagen: str
    rol: str

    @staticmethod
    def from_dict(data:dict):
        persona = Persona.from_dict(data)
        return AuthUser(
            **persona.__dict__,
            id=data['id'] if 'id' in data else data['usuarioId'],
            correo=data['correoElectronico'],
            rol=data['rol']['nombre'],
            imagen=data['perfil']['imagen'],
            nickname=data['perfil']['nickname']
        )
    
@dataclass
class PerfilPersona(AuthUser):
    objetivos: str
    fecha_registro: str

    @staticmethod
    def from_dict(data:dict):
        auth_user = AuthUser.from_dict(data)
        return PerfilPersona(
            **auth_user.__dict__,
            objetivos=data['perfil']['objetivos'],
            fecha_registro=data['perfil']['fechaRegistro']
        )
    
@dataclass
class Suscripcion:
    suscripcion_id: int
    nombre_suscripcion: str
    fecha_inicio: str
    fecha_expiracion: str
    activa: bool

    @staticmethod
    def from_dict(data:dict):
        return Suscripcion(
            suscripcion_id=data['suscripcionId'],
            nombre_suscripcion=data['nombre'],
            fecha_inicio=data['fechaInicio'],
            fecha_expiracion=data['fechaExpiracion'],
            activa=data['activa']
        )    
    
@dataclass 
class PerfilSuscripcion(PerfilPersona, Suscripcion):
    @staticmethod
    def from_suscripcion_perfil(perfil_persona: PerfilPersona, suscripcion: Suscripcion):
        return PerfilSuscripcion(
            **perfil_persona.__dict__,
            **suscripcion.__dict__
        )
