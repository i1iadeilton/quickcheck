package io.bootify.quickcheck.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.bootify.quickcheck.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ClienteUsuarioUnique.ClienteUsuarioUniqueValidator.class
)
public @interface ClienteUsuarioUnique {

    String message() default "{Exists.cliente.usuario}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ClienteUsuarioUniqueValidator implements ConstraintValidator<ClienteUsuarioUnique, Long> {

        private final ClienteService clienteService;
        private final HttpServletRequest request;

        public ClienteUsuarioUniqueValidator(final ClienteService clienteService,
                final HttpServletRequest request) {
            this.clienteService = clienteService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(clienteService.get(Long.parseLong(currentId)).getUsuario())) {
                // value hasn't changed
                return true;
            }
            return !clienteService.usuarioExists(value);
        }

    }

}
