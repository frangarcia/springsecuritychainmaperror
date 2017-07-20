import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class BootStrap {

    def init = { servletContext ->
        SpringSecurityUtils.clientRegisterFilter 'maintenanceModeFilter', SecurityFilterPosition.PRE_AUTH_FILTER.order - 1
    }
    def destroy = {
    }
}
