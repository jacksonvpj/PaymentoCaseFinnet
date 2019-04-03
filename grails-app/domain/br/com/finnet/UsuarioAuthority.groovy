package br.com.finnet

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioAuthority implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Authority authority

	@Override
	boolean equals(other) {
		if (other instanceof UsuarioAuthority) {
			other.usuarioId == usuario?.id && other.authorityId == authority?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (authority) builder.append(authority.id)
		builder.toHashCode()
	}

	static UsuarioAuthority get(long usuarioId, long authorityId) {
		criteriaFor(usuarioId, authorityId).get()
	}

	static boolean exists(long usuarioId, long authorityId) {
		criteriaFor(usuarioId, authorityId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long authorityId) {
		UsuarioAuthority.where {
			usuario == Usuario.load(usuarioId) &&
			authority == Authority.load(authorityId)
		}
	}

	static UsuarioAuthority create(Usuario usuario, Authority authority) {
		def instance = new UsuarioAuthority(usuario: usuario, authority: authority)
		instance.save()
		instance
	}

	static boolean remove(Usuario u, Authority r) {
		if (u != null && r != null) {
			UsuarioAuthority.where { usuario == u && authority == r }.deleteAll()
		}
	}

	static int removeAll(Usuario u) {
		u == null ? 0 : UsuarioAuthority.where { usuario == u }.deleteAll()
	}

	static int removeAll(Authority r) {
		r == null ? 0 : UsuarioAuthority.where { authority == r }.deleteAll()
	}

	static constraints = {
		authority unique: ['usuario']
	}

	static mapping = {
		version false
	}
}
