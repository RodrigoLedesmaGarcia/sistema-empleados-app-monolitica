package com.oracle.www.sistemaempleadosappmonolitica.controller;

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
    @GetMapping("/inicio")
    public String view(){
        return "employees";
    }



    // ----- BUSCAR -----
    @PostMapping("/buscar")
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
        return "new-employees";
    }
    @PostMapping("/nuevo")
    public String crearEmpleado(@Valid @ModelAttribute("employee") EmployeeCreateAndUpdateRequest request, BindingResult result){

        if(result.hasErrors()){
            return "nw-employees";
        }

        service.crearEmpleado(request);
        return "redirect:/employee/nuevo?ok";
    }


    // ----- EDITAR EMPLEADO YA EXISTENTE -----
    @GetMapping("/editar/{empNo}")
    public String fromEditar(@PathVariable Integer empNo, Model model){

        Page<EmployeeProjection> page = service.findAllWithFilters(
                empNo, null, null, null, null, null, null,null,null, 0, 1
        );

        if(page.isEmpty()){
            return "redirect:/employee/inicio=noexiste";
        }

        EmployeeProjection emp = page.getContent().get(0);

        EmployeeCreateAndUpdateRequest request = new EmployeeCreateAndUpdateRequest();
        request.setEmpNo(emp.getEmpNo());
        request.setBirthDate(emp.getBirthDate());
        request.setFirstName(emp.getFirstName());
        request.setLastName(emp.getLastName());
        request.setGender(emp.getGender());
        request.setHireDate(emp.getHireDate());
        request.setDeptNo(emp.getDeptNo());
        request.setFromDate(emp.getFromDate());
        request.setToDate(emp.getToDate());

        model.addAttribute("employee", request);

        return "edit-delete-employee";
    }

    @PostMapping("/editar")
    public String editarEmpleado(@Valid @ModelAttribute("employee") EmployeeCreateAndUpdateRequest request, BindingResult result){

        if(result.hasErrors()){
            return "edit-employee";
        }

        service.editarEmpleado(request);
        return "redirect:/employee/inicio?editado";
    }



    // ----- ELIMINAR EMPLEADO -----
    @PostMapping("/eliminar/{empNo}")
    public String eliminarEmpledo(@PathVariable Integer empNo){
        service.eliminarEmpleado(empNo);
        return "redirect:/employee/inicio?eliminado";
    }


} // fin
