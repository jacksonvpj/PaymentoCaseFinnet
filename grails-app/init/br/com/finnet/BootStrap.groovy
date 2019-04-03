package br.com.finnet

class BootStrap {

    def init = { servletContext ->
    	
		// def role1 = new Authority(authority:"ROLE_USER").save flush:true
		// def user1 = new Usuario(username:"admin",password:"admin").save flush:true
		// UsuarioAuthority.create(user1,role1)
    }

    def destroy = {
    	
    }
}
