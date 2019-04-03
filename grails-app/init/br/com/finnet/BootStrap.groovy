package br.com.finnet

import grails.util.Environment

class BootStrap {
	def springSecurityService
    def init = { servletContext ->
    	if (Environment.current != Environment.TEST) {
			def role1 = new Authority(authority:"ROLE_USER").save flush:true, failOnError:true
			def user1 = new Usuario(username:"admin", password: springSecurityService.encodePassword("admin"), enabled: true).save flush:true, failOnError:true
			UsuarioAuthority.create(user1,role1)    		
    	}
    }

    def destroy = {
    	
    }
}
