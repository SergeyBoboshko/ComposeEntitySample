# 📦 Compose Entity Starter Template

A minimal starter template for projects using **ComposeEntity** — an innovative tool for automatic UI generation and database handling in Android apps with Jetpack Compose.

For full documentation, visit the [Compose Entity KSP Manual](https://wool-fontina-39f.notion.site/Compose-Entity-KSP-1bbac9e714318004866fd9fd627a25e1).

---

## ⚡ Why ComposeEntity?

Building forms connected to a database in Android traditionally involves **tedious work**:
- Writing DAO interfaces manually,
- Creating ViewModels to handle CRUD operations,
- Designing Composable forms for each Entity,
- Handling migrations and database versioning.

**ComposeEntity** solves this by **automatically generating**:
- UI forms,
- Database operations (Room),
- ViewModels and repositories.

With **minimal code**, you can focus on business logic instead of boilerplate.

---

## 🚀 Features

- **Automatic Form Generation** – Define your entity once, get a full UI instantly.
- **Integrated Room Database Handling** – No manual DAOs or ViewModel wiring needed.
- **Maven Central Availability** – Just add the dependency and start.
- **High Customizability** – Customize fields, layouts, and behaviors easily.
- **Rapid Prototyping Friendly** – Build MVPs and admin panels faster than ever.


## 🛠 Installation & Usage

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/SergeyBoboshko/ComposeEntitySample.git
cd ComposeEntitySample
```

### 2️⃣ Rename the Package
Update the package name in the project files:
- Open `AndroidManifest.xml` and change `package="com.example.template"` to your desired package.
- Refactor the package name in the `src/main/java/com/example/template/` directory.

```sh
mv src/main/java/com/example/template src/main/java/com/your/package
```

### 3️⃣ Open in Android Studio
- Open the project in **Android Studio**.
- Ensure you have the latest **Compose Entity** dependencies.

### 4️⃣ Define Your Entities
Create your entities, and **Compose Entity** will handle everything:
```kotlin
@ObjectGeneratorCE(type = GeneratorType.Reference
    , label = "The Meter Zones")
@Parcelize
@Entity(tableName="ref_meterzones")
data class RefMeterZones(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean

): CommonReferenceEntity(id,date,name,isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}
```

And result of this code example on pictures. The fields id and name are presenting on the form by standart way for all of references:

![CE_Example 1](https://github.com/user-attachments/assets/eb172b19-72ce-452e-8364-7761901f6f3e)
![CE_Example 2](https://github.com/user-attachments/assets/dff0d617-fab7-409c-88e1-cd403f362900)

### 5️⃣ Run the Project 🚀
Your UI and database are automatically generated, and you can start using the app immediately.

## 📝 Customization
You can extend the default setup by:
- Adding **custom UI elements** using `customComposable`.
- Modifying **form layouts** for better UX.
- Implementing **custom save logic** within ViewModels.

## 📜 License
This project is licensed under the **MIT License**.

---

For more details, check out the **ComposeEntity** documentation. 🎯

