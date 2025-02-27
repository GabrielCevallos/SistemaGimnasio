from enum import StrEnum

class UserTemplate(StrEnum):
    USER_LIST = 'fragmento/users/list.html'
    USER_REGISTER = 'fragmento/users/register.html'
    USER_INFO = 'fragmento/users/user/info-user.html'
    USER_DELETE = 'fragmento/users/delete.html'



users_urls = {
    'view' : 'fragmento/users/user/info-user.html',
    'delete' : 'fragmento/users/delete.html',
    'list' : 'fragmento/users/list.html',
    'register' : 'fragmento/users/register.html',
}