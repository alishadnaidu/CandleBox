Original App Design Project - README Template
===

# CandleBox

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
CandleBox allows a user to scan the barcode of a candle they’re thinking of purchasing to see if it contains any toxic chemicals that they would not want burning in their house and being released into the environment! The motivation of my app is to promote eco-friendly, non-toxic candles as an alternative to typical paraffin wax candles, which release carcinogens when burned. Another main feature of my app is a tool that allows the user to log hours of candle-burning to see your stats (such as CO2 emission) over time and a song recommendation algorithm that uses the user's top tracks from Spotify, the sentiment of the candle name, and the happiness level of a song to recommend a song that matches the name and scent of a candle.

Watch my demo [here](https://drive.google.com/file/d/1R1BepaWGqBmUoqB8T9bnmfukWQ96ux43/view?usp=sharing)!

[<img width="324" alt="Screenshot 2025-01-14 at 1 48 27 PM" src="https://github.com/user-attachments/assets/6cfc9a0e-8e7b-4dee-bfe2-c2963e9f0aa7" />](https://drive.google.com/file/d/1R1BepaWGqBmUoqB8T9bnmfukWQ96ux43/view?usp=sharing)



### App Evaluation
- **Category:** Environmental
- **Mobile:** Scanning barcode feature makes it uniquely mobile. Includes a login feature to allow you to track your candle-burning habits and stats over a longer period of time. Also, it would provide easily accessible tracking and logging for hours of burning as opposed to a website.
- **Story:** Allows users to become more aware of the specific effects they themselves are having on the environment through burning candles and can see a graph that allows them to visualize their candle-burning habits over time. Gives specific messages about the fossil fuels they are creating to emphasize the negative effect on the environment and promote environmentally-friendly decisions without making them stop using candles altogether.
- **Market:** Anyone that uses candles would benefit from this app — this app would allow candle-users to be more environmentally-friendly and aware of their actions, which is becoming increasingly important with climate change.
- **Habit:** Users can log their hours of burning each time they are burning candles, which is very habit-forming!
- **Scope:** Stripped-down version of this app would allow the user to log their hours of candle-burning and see what kind of affect they are having on the environment.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
1. Your app has multiple views
    * User must be able to log their hours of burning
    * Users must be able to see stats (on what they have logged and what environmental impacts they have had)
3. Your app interacts with a database (e.g. Parse)
    * Use Parse database for storing data on stats for each user
5. You can log in/log out of your app as a user
    * User can log in and out
    * Connect to their Spotify account for song recommendations
7. You can sign up with a new user profile
    * User can create a new account on login page using Parse
9. Your app integrates with at least one SDK (e.g. Google Maps SDK, Facebook SDK) or API (that you didn’t learn about in CodePath)
    * Spotify API and Sentiment Analysis API for song recommendation
11. Your app uses at least one gesture (e.g. double tap to like, e.g. pinch to scale)
    * StepperTouch gesture
13. Your app uses at least one animation (e.g. fade in/out, e.g. animating a view growing and shrinking)
    * Double tap to view recently scanned candles
    * Confetti when user uploads a candle to the database
    * Graph animation
15. Your app incorporates at least one external library to add visual polish
    * StepperTouch, Konfetti, EazeGraph
17. Your app provides opportunities for you to overcome difficult/ambiguous technical problems (more below)
    * Barcode scanning feature
    * Song recommendation algorithm
    
**Optional Nice-to-have Stories**
* For stats page: can see a "history" of the stats in graph form
* User can see recently scanned candles
* Spotify song recommendation

### 2. Screen Archetypes

* Login Screen
   * User can log in
* Stats Screen
   * User can view stats
* Candle burning logging screen
    * User can log hours of burning and set status to environmentally-friendly candle vs. toxic candle
* **Optionals:**
* Barcode scanner screen
    * User can scan barcode of a candle
* Candle details screen
    * User can view the details (mainly ingredients) about the candle the candle they just scanned

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Stats page
* Candle burning logging screen
* Timer screen
* Barcode scanner screen
* Fake candle screen

**Flow Navigation** (Screen to Screen)

* Login screen
   * --> Stats page
* Stats page
    * --> Candle burning logging screen ("Log your candle-burning" button)
* Candle burning logging screen
   * --> Stats page ("View your stats" button)
* Timer Screen
    * --> Candle burning logging screen ("Done burning your candle? Log it here" button)
* Barcode scanner screen
    * --> Candle details screen (automatically redirects to the details screen when finished scanning)
* Fake candle screen
    * --> None, not necessary and will mess with the visuals if there are buttons to go back (can just use tab navigation)
* Candle details screen
    * --> None

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models

User
| Property | Type | Description |
| -------- | -------- | -------- |
|   name   |   string   |   the user's name   |
|   username   |  string    |   the user's username   |
|   hours   |   number   |   hours of candle-burning over lifetime   |
|   gramsCO2   |   number   |   grams of CO2 produced from burning candles over lifetime   |
|   trees   |   number   |   number of trees it would take to offset grams of CO2   |

### Networking
- Stats screen
    - (Read/GET) Load in the lifetime stats for user (hours, gramsCO2, and trees)
- Logging screen
    - (Update/PUT) Update hours of burning after user makes a new log
