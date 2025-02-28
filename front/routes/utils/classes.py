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
    def calcular_edad(fecha_nacimiento: str) -> int:
        fecha_nacimiento = datetime.strptime(fecha_nacimiento, '%Y-%m-%d')
        fecha_actual = datetime.now()
        edad = fecha_actual.year - fecha_nacimiento.year
        if fecha_actual.month < fecha_nacimiento.month or (fecha_actual.month == fecha_nacimiento.month and fecha_actual.day < fecha_nacimiento.day):
            edad -= 1
        return edad 

    @staticmethod
    def from_dict(data: dict) -> 'Persona':
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
class Perfil(Persona):
    perfil_id: int
    nickname: str
    imagen: str
    objetivos: str
    fecha_registro: str

    @staticmethod
    def from_dict(data: dict) -> 'Perfil':
        persona = Persona.from_dict(data)
        return Perfil(
            **persona.__dict__,
            perfil_id=data['perfil']['perfilId'],
            objetivos=data['perfil']['objetivos'],
            fecha_registro=data['perfil']['fechaRegistro'],
            nickname=data['perfil']['nickname'],
            imagen=data['perfil']['imagen']
        )
    
@dataclass
class Usuario(Perfil):
    usuario_id: int
    correoElectronico: str
    rol: str

    @staticmethod
    def from_dict(data: dict) -> 'Usuario':
        persona_perfil = Perfil.from_dict(data)
        return Usuario(
            **persona_perfil.__dict__,
            usuario_id=data['usuarioId'],
            correoElectronico=data['correoElectronico'],
            rol=data['rol']['nombre']
        ) 
    
@dataclass
class Suscripcion:
    suscripcion_id: int
    nombre: str
    fecha_inicio: str
    fecha_expiracion: str
    activa: bool
    precio: float
    persona_id: int

    @staticmethod
    def from_dict(data: dict) -> 'Suscripcion':
        return Suscripcion(
            suscripcion_id=data['suscripcionId'],
            precio=data['precio'],
            nombre=data['tipo'],
            fecha_inicio=data['fechaInicio'],
            fecha_expiracion=data['fechaExpiracion'],
            activa=data['activa'],
            persona_id=data['persona']['personaId']
        )   

@dataclass
class AuthUser():
    usuario_id: int
    nombre: str
    apellido: str
    correoElectronico: str
    nickname: str
    imagen: str
    rol: str

    @staticmethod
    def from_dict(data: dict) -> 'AuthUser':
        return AuthUser(
            usuario_id=data['usuarioId'],
            nombre=data['persona']['nombre'],
            apellido=data['persona']['apellido'],
            correoElectronico=data['correoElectronico'],
            rol=data['rol']['nombre'],
            imagen=data['perfil']['imagen'],
            nickname=data['perfil']['nickname']
        )
    