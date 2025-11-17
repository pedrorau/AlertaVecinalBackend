# FEATURES.md - Dominio de Negocio de Alerta Vecinal

**Last Updated**: 2025-11-17
**Project**: AlertaVecinalBackend

---

## Tabla de Contenidos

1. [Visión del Producto](#visión-del-producto)
2. [Dominio del Negocio](#dominio-del-negocio)
3. [Entidades Principales](#entidades-principales)
4. [Casos de Uso](#casos-de-uso)
5. [Funcionalidades Core](#funcionalidades-core)
6. [Funcionalidades Avanzadas](#funcionalidades-avanzadas)
7. [Reglas de Negocio](#reglas-de-negocio)
8. [Eventos del Sistema](#eventos-del-sistema)

---

## Visión del Producto

**Alerta Vecinal** es una plataforma de seguridad comunitaria que permite a los vecinos de un barrio o comunidad reportar, compartir y estar informados sobre eventos de seguridad e incidentes en su área geográfica.

### Objetivo Principal
Crear una red de comunicación rápida y efectiva entre vecinos para mejorar la seguridad comunitaria mediante alertas en tiempo real sobre eventos relevantes en el vecindario.

### Usuarios Objetivo
- **Vecinos/Residentes**: Personas que viven en un barrio y quieren estar informados
- **Administradores de Barrio**: Líderes comunitarios que gestionan grupos vecinales
- **Autoridades Locales**: Policía local, bomberos, servicios de emergencia (futuro)

---

## Dominio del Negocio

### Contexto: Seguridad Comunitaria

El sistema opera en el contexto de **seguridad y comunicación vecinal**, donde:

- Los vecinos necesitan reportar situaciones sospechosas o emergencias rápidamente
- La información debe llegar solo a las personas cercanas geográficamente
- Las alertas pueden variar desde emergencias graves hasta avisos informativos
- La verificación y moderación de contenido es importante para evitar alarmas falsas
- La privacidad de ubicaciones exactas debe protegerse

### Subdominios

1. **Gestión de Usuarios y Vecindarios**
   - Registro y autenticación de usuarios
   - Perfiles de vecinos
   - Creación y administración de vecindarios/barrios
   - Membresía en múltiples vecindarios

2. **Gestión de Alertas**
   - Creación de alertas
   - Clasificación por categorías
   - Niveles de severidad
   - Geolocalización de eventos
   - Ciclo de vida de alertas (activa, resuelta, archivada)

3. **Notificaciones**
   - Filtrado geográfico (radio de alcance)
   - Push notifications a dispositivos móviles
   - Preferencias de notificación por usuario
   - Canales de comunicación

4. **Interacciones Comunitarias**
   - Comentarios en alertas
   - Validaciones/confirmaciones de vecinos
   - Reportes de alertas falsas
   - Sistema de reputación (futuro)

---

## Entidades Principales

### 1. Usuario (User)
Representa a un vecino registrado en la plataforma.

**Atributos**:
- `id`: UUID - Identificador único
- `email`: String - Email único
- `passwordHash`: String - Contraseña encriptada
- `fullName`: String - Nombre completo
- `phoneNumber`: String? - Teléfono (opcional)
- `profileImage`: String? - URL de imagen de perfil
- `verificationStatus`: Enum - Estado de verificación (pending, verified, rejected)
- `createdAt`: Timestamp
- `updatedAt`: Timestamp

**Relaciones**:
- Pertenece a múltiples Vecindarios
- Crea múltiples Alertas
- Realiza múltiples Comentarios

### 2. Vecindario (Neighborhood)
Representa una comunidad o barrio geográficamente delimitado.

**Atributos**:
- `id`: UUID
- `name`: String - Nombre del barrio (ej: "Villa Crespo", "Palermo Soho")
- `description`: String - Descripción del vecindario
- `boundaries`: Polygon (PostGIS) - Límites geográficos del barrio
- `centerPoint`: Point (PostGIS) - Centro geográfico
- `memberCount`: Int - Número de miembros
- `isActive`: Boolean
- `createdAt`: Timestamp
- `admins`: List<User> - Administradores del vecindario

**Reglas**:
- Debe tener al menos un administrador
- Los límites geográficos no deben superponerse significativamente con otros vecindarios

### 3. Alerta (Alert)
Representa un evento o incidente reportado por un vecino.

**Atributos**:
- `id`: UUID
- `title`: String (max 100 chars) - Título breve y descriptivo
- `description`: String - Descripción detallada
- `category`: AlertCategory (enum) - Tipo de alerta
- `severity`: AlertSeverity (enum) - Nivel de gravedad
- `location`: Point (PostGIS) - Ubicación exacta del evento
- `approximateAddress`: String - Dirección aproximada (sin número exacto)
- `imageUrls`: List<String> - URLs de imágenes adjuntas
- `status`: AlertStatus (enum) - Estado actual
- `createdBy`: User - Usuario que creó la alerta
- `neighborhoodId`: UUID? - Vecindario al que pertenece (opcional)
- `viewCount`: Int - Número de visualizaciones
- `confirmationCount`: Int - Número de confirmaciones de otros vecinos
- `createdAt`: Timestamp
- `updatedAt`: Timestamp
- `resolvedAt`: Timestamp?

**Enumeraciones**:

**AlertCategory**:
- `SECURITY` - Seguridad (robos, personas sospechosas)
- `EMERGENCY` - Emergencias (incendios, accidentes)
- `TRAFFIC` - Tránsito (cortes de calle, accidentes viales)
- `UTILITIES` - Servicios públicos (cortes de luz, agua, gas)
- `COMMUNITY` - Eventos comunitarios (reuniones, avisos)
- `LOST_FOUND` - Objetos/mascotas perdidos y encontrados
- `OTHER` - Otros

**AlertSeverity**:
- `CRITICAL` - Crítico (emergencias que requieren acción inmediata)
- `HIGH` - Alto (situaciones serias que requieren atención)
- `MEDIUM` - Medio (situaciones importantes pero no urgentes)
- `LOW` - Bajo (avisos informativos)

**AlertStatus**:
- `ACTIVE` - Activa (requiere atención)
- `RESOLVED` - Resuelta (ya no requiere atención)
- `ARCHIVED` - Archivada (antigua, para historial)
- `FLAGGED` - Marcada (reportada como inapropiada)
- `DELETED` - Eliminada

### 4. Comentario (Comment)
Comentarios de vecinos sobre una alerta.

**Atributos**:
- `id`: UUID
- `alertId`: UUID - Alerta relacionada
- `userId`: UUID - Usuario que comenta
- `content`: String - Contenido del comentario
- `createdAt`: Timestamp
- `updatedAt`: Timestamp

### 5. Notificación (Notification)
Notificaciones enviadas a usuarios sobre alertas relevantes.

**Atributos**:
- `id`: UUID
- `userId`: UUID - Usuario destinatario
- `alertId`: UUID - Alerta que generó la notificación
- `type`: NotificationType
- `isRead`: Boolean
- `createdAt`: Timestamp

**NotificationType**:
- `NEW_ALERT_NEARBY` - Nueva alerta cerca de tu ubicación
- `ALERT_IN_NEIGHBORHOOD` - Alerta en tu vecindario
- `ALERT_RESOLVED` - Una alerta que seguías fue resuelta
- `COMMENT_ON_ALERT` - Alguien comentó en tu alerta
- `ALERT_CONFIRMATION` - Alguien confirmó tu alerta

---

## Casos de Uso

### UC-01: Registrar Usuario
**Actor**: Vecino no registrado
**Precondición**: El usuario tiene email válido
**Flujo Principal**:
1. Usuario proporciona email, nombre, contraseña
2. Sistema valida que el email no esté registrado
3. Sistema encripta la contraseña
4. Sistema crea cuenta con estado "pending verification"
5. Sistema envía email de verificación
6. Usuario confirma email
7. Sistema activa la cuenta

**Postcondición**: Usuario registrado y verificado

### UC-02: Crear Alerta
**Actor**: Vecino registrado
**Precondición**: Usuario está autenticado
**Flujo Principal**:
1. Usuario selecciona categoría de alerta
2. Usuario ingresa título y descripción
3. Usuario establece ubicación (GPS o manual)
4. Usuario selecciona nivel de severidad
5. Usuario opcionalmente adjunta fotos
6. Sistema valida los datos
7. Sistema crea la alerta con status ACTIVE
8. Sistema determina vecinos cercanos (radio configurable)
9. Sistema envía notificaciones push a vecinos cercanos
10. Sistema muestra confirmación al usuario

**Postcondición**: Alerta creada y notificaciones enviadas

### UC-03: Ver Alertas Cercanas
**Actor**: Vecino registrado
**Precondición**: Usuario está autenticado y tiene ubicación habilitada
**Flujo Principal**:
1. Usuario abre la aplicación
2. Sistema obtiene ubicación actual del usuario
3. Sistema consulta alertas activas en un radio de X km
4. Sistema ordena por distancia y severidad
5. Sistema muestra mapa con marcadores de alertas
6. Sistema muestra lista de alertas con detalles resumidos

**Postcondición**: Usuario ve alertas relevantes

### UC-04: Confirmar Alerta
**Actor**: Vecino registrado
**Precondición**: Usuario ve una alerta creada por otro vecino
**Flujo Principal**:
1. Usuario lee alerta
2. Usuario presiona "Confirmar" si también observa la situación
3. Sistema incrementa contador de confirmaciones
4. Sistema notifica al creador de la alerta
5. Sistema aumenta prioridad de la alerta si tiene múltiples confirmaciones

**Postcondición**: Alerta tiene mayor credibilidad

### UC-05: Resolver Alerta
**Actor**: Creador de la alerta o Administrador
**Precondición**: Alerta está ACTIVE
**Flujo Principal**:
1. Usuario marca alerta como resuelta
2. Usuario opcionalmente agrega comentario de resolución
3. Sistema cambia status a RESOLVED
4. Sistema registra timestamp de resolución
5. Sistema notifica a usuarios que confirmaron la alerta
6. Sistema mantiene alerta visible por 24h antes de archivar

**Postcondición**: Alerta marcada como resuelta

### UC-06: Unirse a Vecindario
**Actor**: Vecino registrado
**Precondición**: Usuario está autenticado
**Flujo Principal**:
1. Usuario busca vecindarios por nombre o ubicación
2. Sistema muestra vecindarios disponibles
3. Usuario solicita unirse a un vecindario
4. Sistema valida que la ubicación del usuario está dentro de los límites
5. Sistema agrega al usuario al vecindario
6. Sistema actualiza contador de miembros

**Postcondición**: Usuario es miembro del vecindario

### UC-07: Filtrar Alertas por Criterios
**Actor**: Vecino registrado
**Precondición**: Usuario está autenticado
**Flujo Principal**:
1. Usuario accede a vista de filtros
2. Usuario selecciona criterios:
   - Categorías específicas
   - Rango de fechas
   - Radio de distancia
   - Nivel de severidad
   - Solo mi vecindario / todos
3. Sistema aplica filtros
4. Sistema muestra alertas que coinciden
5. Sistema permite guardar filtro como favorito

**Postcondición**: Alertas filtradas según preferencias

---

## Funcionalidades Core

### 1. Gestión de Usuarios
- ✅ Registro con email y contraseña
- ✅ Verificación de email
- ✅ Login/Logout
- ✅ Recuperación de contraseña
- ✅ Actualización de perfil
- 🔲 Configuración de preferencias de notificación
- 🔲 Gestión de privacidad

### 2. Gestión de Alertas
- ✅ Crear alerta con ubicación
- ✅ Clasificar por categoría y severidad
- ✅ Adjuntar imágenes (hasta 5)
- ✅ Editar alerta propia (dentro de 30 min)
- ✅ Eliminar alerta propia
- ✅ Marcar alerta como resuelta
- 🔲 Programar alertas recurrentes (ej: corte de agua semanal)

### 3. Descubrimiento de Alertas
- ✅ Ver alertas cercanas en mapa
- ✅ Ver alertas en lista ordenada
- ✅ Filtrar por categoría, severidad, fecha
- ✅ Buscar alertas por texto
- ✅ Ver detalle completo de alerta
- 🔲 Vista de timeline (línea de tiempo)

### 4. Interacciones
- ✅ Comentar en alertas
- ✅ Confirmar alertas de otros
- ✅ Reportar alertas inapropiadas
- 🔲 Reacciones rápidas (útil, no útil)
- 🔲 Compartir alerta fuera de la app

### 5. Notificaciones
- ✅ Push notifications para alertas cercanas
- ✅ Filtro por distancia configurable
- ✅ Filtro por categorías de interés
- ✅ Notificaciones de respuestas a mis alertas
- 🔲 Resumen diario por email
- 🔲 Alertas críticas sin respetar "no molestar"

### 6. Vecindarios
- ✅ Crear vecindario (solo admins)
- ✅ Unirse a vecindario
- ✅ Ver alertas solo de mi vecindario
- ✅ Directorio de vecinos del barrio
- 🔲 Chat grupal por vecindario
- 🔲 Eventos comunitarios

---

## Funcionalidades Avanzadas

### 1. Sistema de Reputación
- Puntos por crear alertas verificadas
- Puntos por confirmar alertas reales
- Penalizaciones por alertas falsas
- Niveles de usuario (novato, vecino, guardián, etc.)
- Badges y reconocimientos

### 2. Análisis y Estadísticas
- Mapa de calor de incidentes
- Tendencias por barrio
- Horarios de mayor incidencia
- Estadísticas personales
- Reportes mensuales

### 3. Integración con Autoridades
- Canal directo con policía local
- Alertas verificadas por autoridades
- Respuestas oficiales en alertas
- Casos abiertos por las autoridades

### 4. Moderación Inteligente
- Detección automática de contenido inapropiado
- Sistema de reportes comunitarios
- Revisión por moderadores
- Bloqueo temporal de usuarios problemáticos

### 5. Funciones Premium (Monetización Futura)
- Alertas sin límite de imágenes
- Notificaciones prioritarias
- Estadísticas avanzadas
- Soporte prioritario
- Sin publicidad

---

## Reglas de Negocio

### RN-01: Creación de Alertas
- Un usuario puede crear máximo 10 alertas activas simultáneamente
- Las alertas deben tener ubicación dentro de un radio de 50km del usuario
- Título: mínimo 10 caracteres, máximo 100
- Descripción: mínimo 20 caracteres, máximo 2000
- Imágenes: máximo 5, tamaño máximo 5MB cada una

### RN-02: Notificaciones Geográficas
- Por defecto, se notifica a usuarios en radio de 2km
- Usuarios pueden configurar radio entre 500m y 10km
- Alertas CRITICAL se notifican hasta 5km independientemente de configuración
- Máximo 50 notificaciones por día por usuario (excepto CRITICAL)

### RN-03: Ciclo de Vida de Alertas
- Alertas ACTIVE por más de 7 días sin actividad pasan a ARCHIVED
- Alertas RESOLVED permanecen visibles por 24 horas
- Alertas ARCHIVED solo visibles en historial
- Usuarios pueden editar alertas solo dentro de 30 minutos de creación
- Solo el creador o administradores pueden resolver/eliminar alertas

### RN-04: Confirmaciones
- Un usuario puede confirmar una alerta solo una vez
- No se puede confirmar la propia alerta
- Alertas con 3+ confirmaciones se marcan como "Verificada"
- Alertas verificadas tienen mayor prioridad en notificaciones

### RN-05: Privacidad de Ubicación
- No se muestra ubicación exacta, solo aproximada (a 100m)
- Dirección mostrada sin número de calle
- Creador puede optar por ocultar ubicación completamente
- Ubicaciones de usuarios nunca se almacenan permanentemente

### RN-06: Vecindarios
- Usuario puede pertenecer a máximo 5 vecindarios
- Vecindarios deben tener mínimo 10 miembros para estar activos
- Administradores pueden moderar contenido de su vecindario
- Límites geográficos no pueden exceder 25km²

### RN-07: Contenido Inapropiado
- Alerta con 5+ reportes se marca como FLAGGED automáticamente
- Contenido FLAGGED se oculta hasta revisión de moderador
- Usuario con 3 alertas marcadas como falsas recibe suspensión temporal
- Spam o publicidad resulta en ban permanente

### RN-08: Comentarios
- Máximo 500 caracteres por comentario
- Solo usuarios del vecindario o dentro del radio pueden comentar
- Comentarios editables solo por 15 minutos
- Sin límite de comentarios por alerta

---

## Eventos del Sistema

### Eventos de Dominio

**AlertCreated**
```kotlin
data class AlertCreated(
    val alertId: UUID,
    val createdBy: UUID,
    val category: AlertCategory,
    val severity: AlertSeverity,
    val location: Point,
    val neighborhoodId: UUID?,
    val timestamp: Instant
)
```
**Efectos**:
- Enviar notificaciones a vecinos cercanos
- Actualizar estadísticas del vecindario
- Registrar en feed de actividad

**AlertConfirmed**
```kotlin
data class AlertConfirmed(
    val alertId: UUID,
    val confirmedBy: UUID,
    val confirmationCount: Int,
    val timestamp: Instant
)
```
**Efectos**:
- Notificar al creador
- Actualizar prioridad si llega a umbral de verificación
- Actualizar reputación del confirmador

**AlertResolved**
```kotlin
data class AlertResolved(
    val alertId: UUID,
    val resolvedBy: UUID,
    val resolutionNote: String?,
    val timestamp: Instant
)
```
**Efectos**:
- Notificar a usuarios que confirmaron
- Actualizar estadísticas
- Programar archivado en 24h

**UserJoinedNeighborhood**
```kotlin
data class UserJoinedNeighborhood(
    val userId: UUID,
    val neighborhoodId: UUID,
    val timestamp: Instant
)
```
**Efectos**:
- Actualizar contador de miembros
- Enviar mensaje de bienvenida
- Habilitar notificaciones del vecindario

---

## Glosario de Términos

- **Alerta**: Notificación de un evento o incidente reportado por un vecino
- **Vecindario**: Comunidad geográficamente delimitada de vecinos
- **Confirmación**: Validación de una alerta por parte de otro vecino que también observó el evento
- **Severidad**: Nivel de urgencia o importancia de una alerta
- **Radio de notificación**: Distancia geográfica en la que se notifica a usuarios sobre una alerta
- **Alerta Verificada**: Alerta que ha sido confirmada por múltiples vecinos
- **Moderador**: Usuario con permisos para revisar y gestionar contenido reportado
- **Reputación**: Sistema de puntos que refleja la credibilidad de un usuario

---

**Nota**: Este documento describe el dominio de negocio y funcionalidades. Para decisiones técnicas de implementación, consultar [CLAUDE.md](./CLAUDE.md).
