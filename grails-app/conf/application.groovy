// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.active = true
grails.plugin.springsecurity.userLookup.userDomainClassName = 'br.com.finnet.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'br.com.finnet.UsuarioAuthority'
grails.plugin.springsecurity.authority.className = 'br.com.finnet.Authority'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/',               access: ['permitAll']],
    [pattern: '/error',          access: ['permitAll']],
    [pattern: '/index',          access: ['permitAll']],
    [pattern: '/index.gsp',      access: ['permitAll']],
    [pattern: '/shutdown',       access: ['permitAll']],
    [pattern: '/dbconsole/**', 	 access: ['permitAll']],
    [pattern: '/api/login',      access: ['permitAll']],
    [pattern: '/api/logout',     access: ['isFullyAuthenticated()']],
    [pattern: '/**',             access: ['isFullyAuthenticated()']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
    [pattern: '/**/favicon.ico', filters: 'none'],
    [pattern: '/dbconsole/**', 	 filters: 'none'],
    [pattern: '/api/**',    	 filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'], // Stateless chain
    [pattern: '/**',        	 filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']   // Traditional chain
]

grails.plugin.springsecurity.filterChain.chainMap = [
    [pattern: '/api/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
	[pattern: '/**', 	 filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName='br.com.finnet.AuthenticationToken'