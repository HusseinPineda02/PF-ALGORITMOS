# Manual de usuario — Sistema CONCYTEC

## Preparación

1. Abra en NetBeans la carpeta que contiene `build.xml`, `src`, `data` y `nbproject`.
2. Ejecute el proyecto con **F6** o la clase `Main` con **Shift+F6**.
3. Para utilizar los datos reales, coloque los archivos en `data/` o impórtelos desde el menú **Archivo**:
   - `investigadores_concytec.csv`
   - `centros_autorizados_2023.csv`

Al importar, los investigadores y centros se cargan en memoria. Luego, las consultas y estadísticas trabajan sobre las tablas hash, no leyendo nuevamente los archivos.

## Pantalla principal

Las tarjetas informan el total de investigadores, centros, colisiones y factor de carga de la tabla hash. Desde el menú **Módulos** se abren Mantenimiento, Búsquedas y Estadísticas.

## Mantenimiento de investigadores

- **Consultar:** ingrese un código RENACYT para recuperar sus datos.
- **Registrar:** complete al menos Código RENACYT e Investigador. No se admiten códigos repetidos.
- **Actualizar:** consulte el código, cambie los campos y pulse Actualizar.
- **Eliminar:** consulte o escriba el código y confirme la eliminación.
- **Limpiar:** prepara el formulario para un nuevo registro.

Registrar, actualizar y eliminar modifican primero la tabla hash y después reescriben el CSV de investigadores para persistir los cambios.

## Búsquedas

En **Módulos > Búsquedas > Investigadores**:

1. Seleccione Código RENACYT, Investigador o Fecha constancia.
2. Escriba el texto en el campo principal: la tabla se actualiza instantáneamente.
3. Para filtrar, active Centro, Provincia y/o Distrito. Cada filtro dispone de su propio espacio horizontal; puede combinar los tres.
4. Pulse los encabezados de la tabla para ordenar visualmente.
5. Use **Exportar CSV / PDF** para guardar los resultados.

En la pestaña **Centros autorizados**, elija Nombre de institución, Área, Subárea o Disciplina, escriba el criterio y presione Enter.

## Estadísticas

Elija un campo de agrupación y pulse **Calcular**. Se puede agrupar por ubicación, institución, área, subárea, disciplina y por los indicadores de investigación, desarrollo o innovación. **Exportar CSV / PDF** guarda el resultado.

## Checklist de la consigna

| Requisito | Estado | Evidencia |
|---|---|---|
| Java SE, Swing, sin BD, Maven o Spring | Cumple | Java SE, Swing, CSV y Ant. |
| Menú principal y formularios | Cumple | Principal, Mantenimiento, Búsquedas y Estadísticas. |
| Sin `ArrayList`, `List`, `HashMap`, `Map`, `Set` | Cumple | El código de negocio usa únicamente TADs propios. |
| Nodo y lista enlazada genéricos | Cumple | `Nodo<K,V>` y `ListaEnlazada<K,V>`. |
| Tabla hash encadenada | Cumple | Arreglo de listas enlazadas en `TablaHashEncadenada<K,V>`. |
| Colisiones por encadenamiento | Cumple | Cada cubeta conserva una lista de pares clave/valor. |
| Hash eficiente para código RENACYT | Cumple | Hash polinómico de cadenas y capacidad prima 20 011. |
| Cargar más de 9 000 registros | Cumple al importar datos reales | La estructura se dimensionó para esa carga. |
| CSV: cabecera, vacíos y espacios | Cumple para el CSV entregado | Lectura línea a línea, cabecera omitida y `trim()`. |
| Altas, actualizaciones y bajas persistentes | Cumple | Se actualiza memoria y se reescribe el CSV. |
| Búsqueda por código, investigador y fecha | Cumple | Consulta instantánea sobre hash en memoria. |
| Filtros centro, provincia y distrito | Cumple | Checkboxes y campos combinables. |
| Búsqueda de centros por sus atributos | Cumple | Nombre, área, subárea y disciplina. |
| Estadísticas solicitadas | Cumple | Ubicación, institución, área, subárea, disciplina e indicadores. |
| Contadores sin mapas de Java | Cumple | `EstadisticaRegistro` en lista enlazada propia. |
| Exportación CSV y PDF | Cumple con alcance básico | CSV completo; PDF de una página con hasta 34 líneas. |
| Exportar búsqueda de centros | Pendiente | La pestaña de centros aún no presenta botón de exportación. |
| Login de usuarios del anexo | Pendiente / opcional | No se implementó autenticación ni almacenamiento de usuarios. |
| Informe Word/PDF y PPTX | Entregable del grupo | El manual apoya la documentación, pero la presentación e informe formal son responsabilidad del equipo. |

El núcleo de Algoritmos y Estructuras de Datos está cubierto: tabla hash encadenada, persistencia, mantenimiento, búsquedas, filtros y estadísticas en memoria. Para un cumplimiento total del anexo quedaría paginar el PDF de resultados largos y añadir exportación a la búsqueda de centros; el login solo debe añadirse si el docente lo exige de forma obligatoria.
