package com.oracle.www.sistemaempleadosappmonolitica.controllers;

import com.oracle.www.sistemaempleadosappmonolitica.entities.DeptEmp;
import com.oracle.www.sistemaempleadosappmonolitica.entities.Employee;
import com.oracle.www.sistemaempleadosappmonolitica.entities.EmployeeCreateAndUpdateRequest;
import com.oracle.www.sistemaempleadosappmonolitica.service.EmployeeServiceImpl;
import com.oracle.www.sistemaempleadosappmonolitica.utils.EmployeeProjection;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private  final EmployeeServiceImpl service;

    public EmployeeController(EmployeeServiceImpl service) {
        this.service = service;
    }

    private String quitarEspacios(String string){
        if(string == null) return null;
        string = string.trim();
        return string.isEmpty() ? null : string;
    }

    //----------------------------
    // vistas en thymeleaf
    //---------------------------


    // --- INICIO ---
    @GetMapping({"/inicio", "/home"})
    public String view(){
        return "employees";
    }



    // ----- BUSCAR -----
    @GetMapping("/buscar")
    public String buscar(
            @RequestParam(required = false) Integer empNo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hireDate,
            @RequestParam(required = false) String deptNo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
            ){

        firstName = quitarEspacios(firstName);
        lastName = quitarEspacios(lastName);
        deptNo = quitarEspacios(deptNo);
        gender = quitarEspacios(gender);

        Page<EmployeeProjection> result = service.findAllWithFilters(
                empNo, birthDate,firstName,lastName,gender, hireDate,deptNo, fromDate, toDate, page,size
        );


        model.addAttribute("results", result);

        if(empNo != null && result.isEmpty()){
            model.addAttribute("error", "usuario inexistente");
        }

        model.addAttribute("empNo", empNo);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("gender", gender);
        model.addAttribute("hireDate", hireDate);
        model.addAttribute("deptNo", deptNo);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "employees";
    }

    // ----- CREAR EMPLEADO -----
    @GetMapping("/nuevo")
    public String fromNuevo(Model model){
        if(!model.containsAttribute("employee")){
            model.addAttribute("employee", new EmployeeCreateAndUpdateRequest());
        }
        return "new-employee";
    }
    @PostMapping("/nuevo")
    public String crearEmpleado(@Valid @ModelAttribute("employee") EmployeeCreateAndUpdateRequest request, BindingResult result){

        if(result.hasErrors()){
            return "new-employee";
        }

        service.crearEmpleado(request);
        return "redirect:/employee/nuevo?ok";
    }


    // ----- EDITAR EMPLEADO YA EXISTENTE -----
    @GetMapping("/editar/{empNo}")
    public String mostrarFormularioEditar(@PathVariable Integer empNo, Model model) {

        Employee employee = service.findById(empNo)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + empNo));

        EmployeeCreateAndUpdateRequest request = new EmployeeCreateAndUpdateRequest();
        request.setEmpNo(employee.getEmpNo());
        request.setBirthDate(employee.getBirthDate());
        request.setFirstName(employee.getFirstName());
        request.setLastName(employee.getLastName());
        request.setGender(employee.getGender());
        request.setHireDate(employee.getHireDate());

        if (employee.getDepartaments() != null && !employee.getDepartaments().isEmpty()) {
            DeptEmp deptEmp = employee.getDepartaments().get(0);
            request.setDeptNo(deptEmp.getId().getDeptNo());
            request.setFromDate(deptEmp.getFromDate());
            request.setToDate(deptEmp.getToDate());
        }

        model.addAttribute("employee", request);
        return "form-editar";
    }

    @PostMapping("/editar")
    public String editarEmpleado(
            @Valid @ModelAttribute("employee") EmployeeCreateAndUpdateRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return "form-editar";
        }

        service.editarEmpleado(request);
        return "redirect:/employee/inicio?editado=true";
    }

    // ----- ELIMINAR EMPLEADO -----
    @PostMapping("/eliminar/{empNo}")
    public String eliminarEmpledo(@PathVariable Integer empNo){
        service.eliminarEmpleado(empNo);
        return "redirect:/employee/inicio?eliminado";
    }


} // fin
