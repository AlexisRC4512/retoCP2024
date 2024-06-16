package com.aramos.retoCP2024.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "Usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_ID")
    private Long id;

    @NotEmpty(message = "El Password no puede ser vacío")
    @Size(min = 6, max = 150, message = "El Password debe tener al menos 6 caracteres")
    @Column(name = "usuario_password", length = 150, nullable = false)
    private String password;

    @Column(name = "nombres", length = 250)
    private String nombres;

    @Column(name = "apellidos", length = 250)
    private String apellidos;

    @Column(name = "estado")
    private boolean estado =true;

    @Email(message = "No es una dirección de correo bien formada")
    @NotEmpty(message = "El email no puede ser vacío")
    @Column(name = "correo", length = 250, nullable = false)
    private String correo;

    @Column(name = "usuario_habilitado", nullable = false)
    private boolean usuarioHabilitado = true;

    @Column(name = "cuenta_no_expirada", nullable = false)
    private boolean cuentaNoExpirada = true;

    @Column(name = "cuenta_no_bloqueada", nullable = false)
    private boolean cuentaNoBloqueada = true;

    @Column(name = "credenciales_no_expirada", nullable = false)
    private boolean credencialesNoExpirada = true;
    @Column(name = "rol_usuario", nullable = false)
    private String RolUsuario;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(RolUsuario));
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return cuentaNoExpirada;
    }

    @Override
    public boolean isAccountNonLocked() {
        return cuentaNoBloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credencialesNoExpirada;
    }

    @Override
    public boolean isEnabled() {
        return estado;
    }
}
