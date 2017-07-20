This repository shows up an issue found with the plugin spring security for Grails. 

Grails version: 3.1.10

Spring security plugin version: 3.1.1

I have created a new custom filter *MaintenceModeFilter* that only want to apply to some pattern urls

This is the current configuration for chainMap in application.groovy

```groovy
grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]
```

I have also register the filter to be run in a specific position. This is the current content in *Bootstrap.groovy*:
 
```groovy
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class BootStrap {

    def init = { servletContext ->
        SpringSecurityUtils.clientRegisterFilter 'maintenanceModeFilter', SecurityFilterPosition.PRE_AUTH_FILTER.order - 1
    }
    def destroy = {
    }
}
```

I have registered the bean as well, as stated in the documentation http://grails-plugins.github.io/grails-spring-security-core/3.1.x/index.html#clientregisterfilter at resources.groovy:

```groovy
import org.springframework.boot.context.embedded.FilterRegistrationBean
import springsecuritychainmaperror.MaintenanceModeFilter

// Place your Spring DSL code here
beans = {
	maintenanceModeFilter(MaintenanceModeFilter)

	maintenanceModeFilterDeregistrationBean(FilterRegistrationBean) {
		filter = ref("maintenanceModeFilter")
		enabled = false
	}
}
```

With this configuration, when requesting for instance /assets/spinner.gif no filter should be taken into account, but actually the filter MaintenanceModeFilter is. Is this a bug or I am missing something?  


To reproduce the problem, start up the application with

```bash
grails run-app
```

and go to the url http://localhost:8080/assets/spinner.gif

That url shouldn't be running the MaintenanceModeFilter but it actually is as you can see in the logs.

```bash
DEBUG org.springframework.security.web.FilterChainProxy - /assets/spinner.gif at position 1 of 1 in additional filter chain; firing Filter: 'MaintenanceModeFilter'
********************* start MaintenanceModeFilter ******************
URI /assets/spinner.gif
********************* end MaintenanceModeFilter ******************
DEBUG org.springframework.security.web.FilterChainProxy - /assets/spinner.gif reached end of additional filter chain; proceeding with original chain
```
