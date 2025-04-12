/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author KyRilloS
 */
public class UpdatePatientDTO {
    
    private int Id;
    private int age;
    private String name;
    private String email;
    private String gender;
    private String phone;
    private String medicalHistory;
    public UpdatePatientDTO(int Id,  String name,int age,String gender ,String email, String phone, String medicalHistory) {
        this.Id = Id;
        this.age = age;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.medicalHistory = medicalHistory;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "UpdatePatientDTO{" + "Id=" + Id + ", age=" + age + ", name=" + name + ", email=" + email + ", gender=" + gender + ", phone=" + phone + ", medicalHistory=" + medicalHistory + '}';
    }

  
    
}
