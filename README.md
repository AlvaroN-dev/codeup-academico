# Sistema de Registro Académico

## Descripción

Sistema de gestión académica desarrollado en Java con interfaz gráfica Swing que permite el registro, cálculo de notas y gestión de estudiantes. El sistema incluye funcionalidades de autenticación, validación de datos, cálculos estadísticos y exportación/importación de datos en formato CSV.

## Características Principales

### 🔐 Sistema de Autenticación
- Login seguro con usuarios predefinidos
- Validación de credenciales
- Manejo de sesiones

### 👨‍🎓 Gestión de Estudiantes
- Registro de estudiantes con validación de datos
- Almacenamiento de información personal (nombre, edad)
- Gestión de hasta 3 notas por estudiante
- Validación de notas en escala 0.0 - 5.0

### 📊 Cálculos Académicos
- Cálculo automático de promedios
- Identificación de nota máxima
- Determinación de estado (aprobado/reprobado)
- Estadísticas generales del grupo

### 📁 Gestión de Archivos
- Exportación de datos a formato CSV
- Importación de datos desde archivos CSV
- Opciones de fusión y reemplazo de datos

### 📈 Estadísticas
- Promedio general del grupo
- Identificación del mejor estudiante
- Conteo de aprobados y reprobados
- Visualización en tabla interactiva

## Credenciales de Acceso

El sistema cuenta con tres usuarios predefinidos para el acceso:

| Usuario | Contraseña | Descripción |
|---------|------------|-------------|
| `admin` | `admin123` | Administrador del sistema |
| `profesor` | `prof2024` | Perfil de profesor |
| `usuario` | `12345` | Usuario estándar |

**Nota:** Todos los usuarios tienen acceso completo a las funcionalidades del sistema.

## Estructura del Proyecto

```
RegistroAcademico/
├── src/
│   └── RegistroAcademico/
│       └── codeup/
│           └── academico/
│               ├── Main.java                    # Clase principal
│               ├── domain/                      # Entidades del dominio
│               │   ├── Estudiante.java         # Clase Estudiante
│               │   └── Nota.java               # Clase Nota
│               ├── service/                     # Servicios de negocio
│               │   ├── CalculoService.java     # Cálculos académicos
│               │   ├── RegistroEstudiantesService.java # Gestión de estudiantes
│               │   ├── UsuarioService.java     # Autenticación
│               │   └── ArchivoService.java     # Gestión de archivos CSV
│               └── ui/                          # Interfaz gráfica
│                   ├── JFrameLogin.java        # Ventana de login
│                   ├── JFrameLogin.form        # Diseño del login
│                   ├── JFrameRegistroEstudiante.java # Ventana principal
│                   └── JFrameRegistroEstudiante.form # Diseño ventana principal
```

## Requisitos del Sistema

- **Java:** JDK 8 o superior
- **IDE recomendado:** NetBeans (para edición de formularios)
- **Sistema operativo:** Windows, macOS, Linux

## Instalación y Ejecución

### 1. Compilación
```bash
javac -cp . RegistroAcademico/codeup/academico/Main.java
```

### 2. Ejecución
```bash
java RegistroAcademico.codeup.academico.Main
```

### 3. Uso con IDE
1. Importar el proyecto en NetBeans o Eclipse
2. Compilar y ejecutar la clase `Main.java`

## Guía de Uso

### Inicio de Sesión
1. Ejecutar la aplicación
2. Ingresar usuario y contraseña (ver tabla de credenciales)
3. Hacer clic en "Login"

### Registro de Estudiantes
1. Completar los campos obligatorios:
   - **Nombre:** 2-50 caracteres, solo letras
   - **Edad:** Entre 15-100 años
   - **Notas:** Valores entre 0.0-5.0, máximo 2 decimales
2. Hacer clic en "Guardar" para agregar a la lista
3. Usar "Calcular" para ver resultados individuales

### Funcionalidades Adicionales
- **Limpiar:** Borrar todos los campos del formulario
- **Estadísticas:** Ver resumen general del grupo
- **Guardar CSV:** Exportar datos a archivo
- **Cargar CSV:** Importar datos desde archivo
- **Cerrar Sesión:** Regresar al login

## Validaciones Implementadas

### Datos del Estudiante
- **Nombre:** No vacío, longitud 2-50 caracteres, solo letras y espacios
- **Edad:** Rango 15-100 años
- **Notas:** Rango 0.0-5.0, máximo 2 decimales

### Reglas de Negocio
- Máximo 3 notas por estudiante
- Promedio mínimo 3.0 para aprobar
- Validación de duplicados por nombre (opcional)

## Formato CSV

El sistema maneja archivos CSV con el siguiente formato:
```csv
id,nombre,edad,nota1,nota2,nota3
uuid-123,Juan Pérez,20,4.5,3.8,4.2
uuid-456,María García,22,3.0,2.5,3.5
```

## Manejo de Errores

El sistema incluye manejo robusto de errores:
- Validación en tiempo real de formularios
- Mensajes de error descriptivos
- Logging de errores del sistema
- Confirmaciones para acciones críticas

## Características Técnicas

### Patrones de Diseño
- **MVC:** Separación de lógica de negocio y presentación
- **Service Layer:** Servicios especializados por funcionalidad
- **DTO:** Objetos de dominio inmutables (Nota)

### Seguridad
- Validación de entrada en todas las capas
- Limpieza automática de contraseñas en memoria
- Validación de tipos de archivo

### Usabilidad
- Interfaz intuitiva con validación visual
- Mensajes de confirmación y error claros
- Navegación fluida entre pantallas
- Atajos de teclado (Enter para login)

## Troubleshooting

### Problemas Comunes

**Error al iniciar:**
- Verificar que Java esté instalado correctamente
- Comprobar que todas las clases estén compiladas

**Problemas con CSV:**
- Verificar que el archivo tenga la estructura correcta
- Comprobar permisos de lectura/escritura
- Validar que los datos cumplan las reglas de negocio

**Interfaz no responde:**
- Verificar que no haya procesos bloqueantes
- Reiniciar la aplicación si es necesario

## Futuras Mejoras

- Base de datos para persistencia
- Más tipos de usuario con permisos diferenciados
- Reportes en PDF
- Gráficos estadísticos
- API REST para integración
- Internacionalización (i18n)
