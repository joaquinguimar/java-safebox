package es.jguimar.safeboxAPI.service.impl;
import es.jguimar.safeboxAPI.entity.SafeBox;
import es.jguimar.safeboxAPI.repository.SafeBoxRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor //Constructor-Based Dependency Injection
public class MongoUserDetailsService implements UserDetailsService{

  private SafeBoxRepository safeBoxRepository;

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Optional<SafeBox> safebox = safeBoxRepository.findByName(name);
    if(safebox.isEmpty()) {
      throw new UsernameNotFoundException("Safebox not found for this name");
    }
    List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
    return new User(safebox.get().getName(), safebox.get().getPassword(), authorities);
  }
}