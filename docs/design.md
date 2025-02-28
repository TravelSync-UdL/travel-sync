# Decisions Arquitectòniques

```mermaid
classDiagram
    class User {
        <<Entity>>
        +int id
        +string username
        +string email
        +string password
        +string profile_pic
        +string language
        +string preferred_currency
        +string preferred_transport
        +createUser(username, email, password)
        +getUserProfile(userId)
        +updateUserProfile(userId, newInfo)
        +deleteUser(userId)
        +setUserLanguage(userId, languageCode)
    }

    class Trip {
        <<Entity>>
        +int id
        +string title
        +string destination
        +date start_date
        +date end_date
        +int user_id
        +string privacy
        +createTrip(userId, title, destination, startDate, endDate)
        +getTripDetails(tripId)
        +updateTrip(tripId, updatedInfo)
        +deleteTrip(tripId)
        +getUserTrips(userId)
        +loadTripDetails()
        +editTrip()
        +deleteTrip()
        +addActivityToTrip()
        +removeActivityFromTrip()
        +viewTripMap()
    }

    class Preference {
        <<Entity>>
        +int user_id
        +string preferred_language
        +string preferred_currency
        +string preferred_transport
        +getUserPreferences(userId)
        +setUserPreferences(userId, preferences)
    }

    class Map {
        <<Entity>>
        +int id
        +int trip_id
        +string map_data
        +createMap(tripId, mapData)
        +getMapForTrip(tripId)
    }

    class Post {
        <<Entity>>
        +int id
        +string content
        +string images
        +date created_at
        +int user_id
        +int trip_id
        +createPost(userId, tripId, content, images)
        +getPostsForTrip(tripId)
        +deletePost(postId)
        +editPost()
        +deletePost()
        +likePost()
        +unlikePost()
    }

    class Friend {
        <<Entity>>
        +int user_id
        +int friend_id
        +addFriend(userId, friendId)
        +removeFriend(userId, friendId)
        +getUserFriends(userId)
    }

    class Settings {
        <<Entity>>
        +int user_id
        +boolean notifications_enabled
        +string language
        +updateUserSettings(userId, settings)
    }

    class Activity {
        <<Entity>>
        +int id
        +string title
        +string location
        +date time
        +int trip_id
        +createActivity(tripId, title, location, time)
        +getActivitiesForTrip(tripId)
        +deleteActivity(activityId)
    }

    class Guide {
        <<Entity>>
        +int id
        +string name
        +string description
        +string image
        +string link
        +createGuide(name, description, image, link)
        +getGuideDetails(guideId)
        +getAllGuides()
        +fetchLocalAttractions()
        +fetchRestaurants()
        +fetchTransportOptions()
        +getWeatherForecast()
    }

    class Search {
        <<Entity>>
        +string search_term
        +string filter
        +searchTrips(query)
        +searchDestinations(query)
        +searchFlights(query)
        +searchHotels(query)
        +filterSearchResults(filters)
    }

    User "1" --> "many" Trip : plans
    User "1" --> "many" Post : creates
    User "1" --> "many" Comment : writes
    User "1" --> "many" Friend : connects with
    User "1" --> "1" Settings : has
    User "1" --> "1" Preference : defines

    Trip "1" --> "many" Post : related to
    Trip "1" --> "many" Activity : includes
    Trip "1" --> "1" Map : has

    Post "1" --> "many" Comment : has

    Guide "1" --> "many" Trip : linked to
    Search "1" --> "many" Trip : searches
    Search "1" --> "many" Post : searches
    Search "1" --> "many" User : searches
