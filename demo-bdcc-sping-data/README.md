# 📦 Gestion des produits — Spring Boot, Spring Data JPA, H2 & API REST

Application Spring Boot qui illustre la **persistance avec Spring Data JPA / Hibernate** sur une base **H2**, puis l'exposition des données via une **API REST** documentée avec **OpenAPI (Swagger UI)** et testée avec **Postman**.

> Vidéo de référence (First Demo) : https://www.youtube.com/watch?v=UXs39oVoaSs

---

## 📑 Sommaire

1. [Objectifs](#-objectifs)
2. [Technologies](#-technologies)
3. [Architecture](#-architecture)
4. [Étapes de réalisation](#-étapes-de-réalisation)
5. [Tests de la couche DAO](#-tests-de-la-couche-dao)
6. [API REST](#-api-rest)
7. [Captures d'écran](#-captures-décran)
8. [Exécution](#-exécution)
9. [Conclusion](#-conclusion)

---

## 🎯 Objectifs

- Installer **IntelliJ IDEA Ultimate**
- Créer un projet avec **Spring Initializr** (JPA, H2, Spring Web, Lombok)
- Créer l'entité JPA `Product`
- Configurer l'unité de persistance (`application.properties`)
- Créer le repository basé sur **Spring Data**
- Tester les opérations de gestion des produits :
  - Ajouter des produits
  - Consulter tous les produits
  - Consulter un produit
  - Chercher des produits
  - Mettre à jour un produit
  - Supprimer un produit
- Exposer ces opérations via une **API REST** et les tester (navigateur, Swagger UI, Postman)

---

## 🧰 Technologies

| Technologie | Rôle |
|---|---|
| IntelliJ IDEA Ultimate | IDE |
| Java 17+ | Langage |
| Spring Boot | Socle applicatif |
| Spring Web | Contrôleur REST |
| Spring Data JPA / Hibernate | ORM et accès aux données |
| H2 Database | Base de données en mémoire + console web |
| Lombok | Génération des getters/setters/constructeurs |
| springdoc-openapi | Documentation OpenAPI / Swagger UI |
| Postman | Test des endpoints |
| Maven | Gestion des dépendances |

---

## 🗂️ Architecture

```
src/main
├── java/com/example/products
│   ├── ProductsApplication.java
│   ├── entities
│   │   └── Product.java
│   ├── repository
│   │   └── ProductRepository.java
│   └── web
│       └── ProductRestController.java
└── resources
    └── application.properties
```

> Adaptez le nom du package à celui de votre projet.

---

## 🪜 Étapes de réalisation

### 1. Création du projet (Spring Initializr dans IntelliJ)

Dépendances : **Spring Data JPA, H2 Database, Spring Web, Lombok**.

Dépendance supplémentaire pour OpenAPI (`pom.xml`) :

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

### 2. Entité JPA `Product`

```java
@Entity
@Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
```

### 3. Configuration de l'unité de persistance (`application.properties`)

```properties
spring.application.name=products
server.port=8080

# H2 en mémoire
spring.datasource.url=jdbc:h2:mem:products-db
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# Hibernate
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

### 4. Interface `ProductRepository` (Spring Data)

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContains(String keyword);

    List<Product> findByPriceGreaterThan(double price);

    @Query("select p from Product p where p.name like :kw")
    List<Product> search(@Param("kw") String keyword);
}
```

---

## 🧪 Tests de la couche DAO

Les tests sont exécutés au démarrage grâce à un `CommandLineRunner`. Les résultats s'affichent dans le **terminal**.

```java
@SpringBootApplication
public class ProductsApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 1. Ajouter des produits
        productRepository.save(new Product(null, "Computer", 5400, 12));
        productRepository.save(new Product(null, "Printer", 1200, 11));
        productRepository.save(new Product(null, "Smart Phone", 12000, 33));

        // 2. Consulter tous les produits
        productRepository.findAll().forEach(p -> System.out.println(p.toString()));

        // 3. Consulter un produit
        Product product = productRepository.findById(1L).orElse(null);
        if (product != null) {
            System.out.println("**********");
            System.out.println(product.getId());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getQuantity());
            System.out.println("**********");
        }

        // 4. Chercher des produits
        productRepository.findByNameContains("C").forEach(System.out::println);
        productRepository.search("%P%").forEach(System.out::println);
        productRepository.findByPriceGreaterThan(3000).forEach(System.out::println);

        // 5. Mettre à jour un produit
        product.setPrice(6000);
        productRepository.save(product);

        // 6. Supprimer un produit
        productRepository.deleteById(1L);
    }
}
```

---

## 🌐 API REST

### Contrôleur `ProductRestController`

```java
@RestController
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product productById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @GetMapping("/products/search")
    public List<Product> search(@RequestParam(name = "kw", defaultValue = "") String kw) {
        return productRepository.findByNameContains(kw);
    }

    @PostMapping("/products")
    public Product save(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/products/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
```

### Endpoints

| Méthode | URL | Description |
|---|---|---|
| `GET` | `/products` | Liste de tous les produits |
| `GET` | `/products/{id}` | Produit par identifiant |
| `GET` | `/products/search?kw=...` | Recherche par mot-clé |
| `POST` | `/products` | Ajouter un produit |
| `PUT` | `/products/{id}` | Modifier un produit |
| `DELETE` | `/products/{id}` | Supprimer un produit |

### Liens utiles

| Ressource | URL |
|---|---|
| Liste des produits (navigateur) | http://localhost:8080/products |
| Un produit précis | http://localhost:8080/products/1 |
| Console H2 | http://localhost:8080/h2-console |
| Documentation OpenAPI (JSON) | http://localhost:8080/v3/api-docs |
| Swagger UI | http://localhost:8080/swagger-ui.html |

### Exemple de corps JSON (POST / PUT)

```json
{
  "name": "Tablet",
  "price": 3500,
  "quantity": 8
}
```

---

## 📸 Captures d'écran

> 📌 Enregistrez chaque capture dans le dossier `screenshots/` avec le nom indiqué, puis elle s'affichera automatiquement ici.

### 1. Connexion à la console H2

Page de login de la console H2 : `JDBC URL = jdbc:h2:mem:products-db`, `User = sa`, mot de passe vide.

<img src="images/1.png" width="700">

---

### 2. Résultat des opérations dans le terminal

Affichage des produits ajoutés, du produit consulté et des résultats de recherche dans la console IntelliJ.

<img src="images/2.png" width="700">

---

### 3. `SELECT * FROM PRODUCTS` dans la console H2

Requête exécutée dans la console H2 et affichage du tableau des produits.

<img src="images/3.png" width="700">

---

### 4. Affichage des produits dans le navigateur

Appel de `http://localhost:8080/products` : le JSON avec tous les produits et leurs informations.

<img src="images/4.png" width="700">
---

### 5. Whitelabel Error Page

Page d'erreur affichée lorsque l'URL n'est pas mappée (avant la création du contrôleur ou URL incorrecte).

<img src="images/5.png" width="700">
---

### 6. Affichage des produits dans le terminal

Les produits listés dans la console après exécution du `CommandLineRunner`.

<img src="images/7.png" width="700">
---

### 7. Affichage d'un produit précis via l'URL

Appel de `http://localhost:8080/products/1`.

<img src="images/8.png" width="700">
---

### 8. Documentation OpenAPI

Page Swagger UI et liste des endpoints.

<img src="images/9.png" width="700">
---

### 9. `product-rest-controller` dans Swagger

Le groupe d'endpoints du contrôleur REST.

<img src="images/10.png" width="700">
---

### 10. Identification du paramètre `id` dans OpenAPI

Sélection d'un produit par son `id` (paramètre de chemin) via *Try it out*.

<img src="images/11.png" width="700">
<img src="images/12.png" width="700">
---

### 11. Identification du paramètre `kw` dans OpenAPI

Recherche de produits par mot-clé.

<img src="images/13.png" width="700">
<img src="images/19.png" width="700">
---

### 12. Les méthodes GET et POST

Vue des endpoints `GET` et `POST` dans Swagger.

<img src="images/14.png" width="700">
<img src="images/15.png" width="700">

---

### 13. Ajout de la méthode DELETE

Endpoint `DELETE /products/{id}` ajouté au contrôleur et visible dans Swagger.
<img src="images/17.png" width="700">
<img src="images/18.png" width="700">

---

### 14. Ouverture de Postman

<img src="images/20.png" width="700">

---

### 15. Lien entre Postman et l'URL de l'application

Requête `GET http://localhost:8080/products` configurée dans Postman.

<img src="images/23.png" width="700">
<img src="images/23.png" width="700">
---

### 16. Affichage des produits dans Postman

Réponse JSON avec le code `200 OK`.

<img src="images/22.png" width="700">
<img src="images/24.png" width="700">
<img src="images/25.png" width="700">

---

## ▶️ Exécution

### Prérequis

- JDK 17 ou supérieur
- Maven 3.8+ (ou le wrapper `mvnw` du projet)
- IntelliJ IDEA Ultimate (recommandé)

### Lancer l'application

```bash
git clone <url-de-votre-depot>
cd products
./mvnw spring-boot:run
```

Ou depuis IntelliJ : clic droit sur `ProductsApplication` → **Run**.

### Accès

- API : http://localhost:8080/products
- Swagger UI : http://localhost:8080/swagger-ui.html
- Console H2 : http://localhost:8080/h2-console (JDBC URL : `jdbc:h2:mem:products-db`, user : `sa`)

---

## ✅ Conclusion

Ce travail a permis de mettre en pratique :

- la configuration d'un projet **Spring Boot** avec Spring Initializr ;
- le **mapping objet-relationnel** avec JPA / Hibernate sur une base H2 ;
- les opérations **CRUD** et les requêtes de recherche avec **Spring Data** ;
- la création d'une **API REST** documentée avec **OpenAPI** et testée avec **Postman**.

---

👤 **Auteur** : MATMOUR YASSMINE
