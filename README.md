# Proyecto de Ejemplo con Quarkus y Arquitectura Hexagonal

Este es un proyecto de ejemplo que demuestra la implementación de la arquitectura hexagonal (puertos y adaptadores) utilizando Quarkus y Java. La arquitectura hexagonal busca separar las preocupaciones, permitiendo que el núcleo de la aplicación (dominio) permanezca independiente de la infraestructura y las interfaces externas.

## ¿Qué es la Arquitectura Hexagonal?

La arquitectura hexagonal, también conocida como Ports and Adapters, organiza la aplicación en un núcleo central que contiene la lógica de negocio (dominio). Este núcleo se conecta a través de "puertos" que definen las interfaces para interactuar con el exterior, como bases de datos, APIs, UI, etc. Los "adaptadores" implementan esos puertos, permitiendo la comunicación con diferentes tecnologías o interfaces sin afectar el dominio.


![Diagrama de Arquitectura Hexagonal](./images/arq-hex.png)


## Estructura del Proyecto

- `src/main/java/com/resolutions/adapters`: Código fuente adaptadores: In (Controladores), out (Bridges y Repositorios).
- `src/main/java/com/resolutions/applications`: Código fuente del núcleo: Casos de usuos y puertos.
- `src/main/java/com/resolutions/modelo`: Código fuente del núcleo: Entidades.
- `src/main/resources/`: Configuración.
- `test/`: Pruebas unitarias y de integración.

## Características principales

- Implementación en Quarkus para un arranque rápido y eficiente.
- Separación clara entre lógica de negocio y adaptadores tecnológicos.
- Fácil de extender con nuevos adaptadores o puertos.

## Cómo ejecutar

1. Clonar el repositorio:
```bash
git clone https://github.com/ronaldsanchez/hexagonal-arquitecture.git
```
2. Correr una imagen de oracle. Ejecute los comandos de Podman ubicados en el archivo: `podman/Oracle24ai_FREE.txt`

3. Correr el aplicativo:
```bash
./mvnw quarkus:dev
```

4. Tester el aplicativo:
```bash
curl -d '{"catDesc":"Menu Principal","codUsr":"rsanchez"}' -H "Content-Type: application/json" POST http://localhost:8080/api/catalogos -w "\n" -v
```

## Endpoints de la API

### Catálogos

#### POST /api/catalogos
Crear un nuevo catálogo.

**Request:**
```json
{
  "catDesc": "Descripción del catálogo",
  "codUsr": "usuario",
  "items": []
}
```

**Response:** 
- Status: 201 Created
- Location: URI del catálogo creado

---

#### GET /api/catalogos/{id}
Obtener un catálogo por ID.

**Parameters:**
- `id` (path): ID del catálogo

**Response (200):**
```json
{
  "codCat": 1,
  "catDesc": "Descripción",
  "codUsr": "usuario",
  "fecha": "2025-11-12T10:00:00Z",
  "items": []
}
```

---

#### GET /api/catalogos?descripcion={descripcion}
Buscar un catálogo por descripción.

**Parameters:**
- `descripcion` (query): Descripción del catálogo a buscar

**Response (200):**
```json
{
  "codCat": 1,
  "catDesc": "Descripción",
  "codUsr": "usuario",
  "fecha": "2025-11-12T10:00:00Z",
  "items": []
}
```

---

#### GET /api/catalogos/paginated/list?page={page}&size={size}
Obtener catálogos paginados.

**Parameters:**
- `page` (query, optional): Número de página (default: 0)
- `size` (query, optional): Cantidad de items por página (default: 10)

**Response (200):**
```json
{
  "content": [
    {
      "codCat": 1,
      "catDesc": "Descripción 1",
      "codUsr": "usuario",
      "fecha": "2025-11-12T10:00:00Z",
      "items": []
    },
    {
      "codCat": 2,
      "catDesc": "Descripción 2",
      "codUsr": "usuario",
      "fecha": "2025-11-12T11:00:00Z",
      "items": []
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 25,
  "totalPages": 3
}
```

**Ejemplos:**
```bash
# Obtener página 0 con 10 items (default)
curl http://localhost:8080/api/catalogos/paginated/list

# Obtener página 1 con 15 items por página
curl http://localhost:8080/api/catalogos/paginated/list?page=1&size=15
```

---

#### PUT /api/catalogos/{id}
Modificar un catálogo existente.

**Parameters:**
- `id` (path): ID del catálogo

**Request:**
```json
{
  "catDesc": "Nueva descripción",
  "codUsr": "usuario",
  "items": []
}
```

**Response:** 
- Status: 204 No Content