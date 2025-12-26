# Pixels

The `pixels()` method provides access to the pixels API endpoints.

## Get all pixels

This method returns a list of all pixels available to the user.

```kotlin
val pixels = umami.pixels().getPixels()
```

### Parameters

- `search` (optional): A search string to filter the results.
- `page` (optional): The page number to return.
- `pageSize` (optional): The number of results to return per page.

## Get a pixel by ID

This method returns the details of a specific pixel.

```kotlin
val pixel = umami.pixels().getPixel("pixel-id")
```

## Update a pixel

This method updates the details of a specific pixel.

```kotlin
val pixel = umami.pixels().updatePixel(
    pixelId = "pixel-id",
    request = UpdatePixelRequest(
        name = "New Pixel Name",
        slug = "new-pixel-slug"
    )
)
```

## Delete a pixel

This method deletes a specific pixel.

```kotlin
umami.pixels().deletePixel("pixel-id")
```
