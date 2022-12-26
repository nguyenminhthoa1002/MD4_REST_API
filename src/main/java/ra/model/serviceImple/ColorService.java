package ra.model.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.entity.Color;
import ra.model.service.IColorService;
import ra.model.repository.ColorRepository;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = SQLException.class)
public class ColorService implements IColorService<Color,Integer> {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> searchColor(String searchName) {
        return colorRepository.searchColorByColorName(searchName);
    }

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color findById(Integer id) {
        return colorRepository.findById(id).get();
    }

    @Override
    public Color saveOrUpdate(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public void delete(Integer id) {
        colorRepository.deleteById(id);
    }
}
