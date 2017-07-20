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
