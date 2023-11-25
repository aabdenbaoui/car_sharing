package carsharing.DAO;

import carsharing.entities.Company;

import java.util.List;

public interface CompanyDao {
    List<Company> findAllComapnies();
    Company findById(int id);
    void add(Company developer);
    void update(Company developer);
    void deleteById(int id);
}
