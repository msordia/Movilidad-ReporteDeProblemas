package itesm.mx.movilidad_reportedeproblemas.Services;

//////////////////////////////////////////////////////////
//Clase: IContainer
// Descripción: Esta interfaz obtiene contenedores.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public interface IContainer {
    Object getComponent(Class<?> $class, int code);
}
