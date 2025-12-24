# Websites API

The `Websites` API provides a convenient way to interact with the website-related endpoints of the Umami API.

To get started, you can access the `Websites` API from your `Umami` instance:

```kotlin
val umami = Umami(website = "your-website-id")
val websitesApi = umami.websites()
```

## Get all websites

You can retrieve a list of all websites available to the user. This method supports pagination and searching.

```kotlin
val websites = websitesApi.getWebsites(
    search = "example.com",
    page = 1,
    pageSize = 10
)
```

## Get a specific website

To retrieve a single website by its ID, use the `getWebsite` method:

```kotlin
val websiteId = "your-website-id"
val website = websitesApi.getWebsite(websiteId)
```

## Create a new website

You can create a new website by providing a name and domain:

```kotlin
val newWebsite = websitesApi.createWebsite(
    name = "My New Website",
    domain = "new-website.com"
)
```

## Update a website

To update an existing website, you need its ID and the new data:

```kotlin
val websiteId = "your-website-id"
val updatedWebsite = websitesApi.updateWebsite(
    websiteId = websiteId,
    name = "My Updated Website Name"
)
```

## Delete a website

You can delete a website by its ID. This operation does not return any data.

```kotlin
val websiteId = "your-website-id"
websitesApi.deleteWebsite(websiteId)
```

## Reset a website

To reset all data for a website, use the `resetWebsite` method. This operation also does not return any data.

```kotlin
val websiteId = "your-website-id"
websitesApi.resetWebsite(websiteId)
```
