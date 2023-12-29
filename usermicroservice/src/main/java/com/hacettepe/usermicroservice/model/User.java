package com.hacettepe.usermicroservice.model;

import com.hacettepe.usermicroservice.utils.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    @Column(name = "cv")
    private String cv; // as link or filename

    @Column(name = "github")
    private String github; // link

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info")
    private PaymentInfo paymentInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }

    private void setEnabled(boolean enabled)
    {
        this.isEnabled = enabled;
    }
}
