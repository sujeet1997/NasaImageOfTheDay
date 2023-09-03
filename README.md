# NASA Image of the Day App

This Android app displays NASA's "Image of the Day" along with its description using NASA's API.

## Prerequisites

- Android Studio
- Kotlin
- API Key from NASA's Open API portal
- 
## Getting Started

1. Clone this repository to your local machine.
2. Open the project in Android Studio.
3. Add your NASA API key to the `build.gradle` file as follows:
   buildConfigField "String", "APIKey", "\"YOUR_API_KEY\""
4. Build and run the app on an Android emulator or physical device.

## API Key Instructions

To obtain an API key from NASA's Open API portal:

1. Visit [NASA's API portal](https://api.nasa.gov).
2. Sign up for an API key.
3. Insert your API key to the `build.gradle` file as follows:
   buildConfigField "String", "APIKey", "\"YOUR_API_KEY\""

## Usage

- The app will automatically load the daily image on launch.
- To manually refresh the image, click the refresh button.
- If the image is a video, click on the play button overlay to start playback.

## Acknowledgments

- Thanks to the [ExoPlayer](https://github.com/google/ExoPlayer) team for the video playback functionality.
- Image resources provided by [NASA](https://www.nasa.gov).



![screen1](https://github.com/sujeet1997/NasaImageOfTheDay/assets/81901334/5707e3b0-b9b5-4329-8b0a-f98322992140)


![screen2](https://github.com/sujeet1997/NasaImageOfTheDay/assets/81901334/f6f52661-7431-447e-bd05-c85eeb9aa373)

