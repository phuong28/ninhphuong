package tacos.data;
import org.springframework.stereotype.Service;

import tacos.Taco;
@Service
public interface TacoRepository {
	Taco save(Taco design);
}
