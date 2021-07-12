Original App Design Project - README Template
https://hackmd.io/8eh0b91CQEaSSj3N0n4hEg#Product-Spec
===

# candle box

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
The Candle App allows a user to scan the barcode of a candle they’re thinking of purchasing to see if it contains any toxic chemicals that they would not want burning in their house and being released into the environment! The app will also promote eco-friendly, non-toxic candles as an alternative to typical paraffin wax candles, which release carcinogens when burned. Other potential features may include setting a timer for your candle so you don’t keep it burning for too long and risk starting a fire, a "fake" candle, a page where you can log your hours of burning a candle + a stats page to show you hours of burning, toxic chemicals released, amount of fossil fuels burned, etc. Alternatively, if you specify that you are using an environmentally-friendly candle, there will be stats on the amount of fossil fuels you have saved/avoided burning by using a non-toxic candle.

### App Evaluation
- **Category:** Environmental
- **Mobile:** Scanning barcode feature makes it uniquely mobile. Perhaps can include a login feature to allow you to track your candle-burning habits and stats over a longer period of time. Also, it would provide easily accessible tracking and logging for hours of burning as opposed to a website.
- **Story:** Allows users to become more aware of the specific effects they themselves are having on the environment through burning candles. Gives specific data on the fossil fuels they are creating to really emphasize the negative effect on the environment and promote environmentally-friendly decisions without making them stop using candles altogether.
- **Market:** Anyone that uses candles would benefit from this app — this app would allow candle-users to be more environmentally-friendly and aware of their actions, which is becoming increasingly important with climate change.
- **Habit:** Users can log their hours of burning each time they are burning candles, which is very habit-forming!
- **Scope:** Stripped-down version of this app would allow the user to log their hours of candle-burning and see what kind of affect they are having on the environment.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User must be able to log their hours of burning
* Users must be able to see stats (on what they have logged and what environmental impacts they have had)
* User can create a new account or potentially connect their account to Google or Facebook account if the first option is not viable
* User can log in and out
* User can specify if they are using an environmentally friendly candle, which will allow them to see their stats on their positive impact on the environment (or rather, how they have avoided having a negative effect)

**Optional Nice-to-have Stories**

* Ability to connect to social media (ex. Twitter, Facebook, or Instagram) to share your stats or share a picture of your eco-friendly candle
* User can set a timer for burning a candle to avoid over-burning it and risk starting a fire
* "Fake Candle" page with a gif/video of a burning candle (just for fun!)
* Barcode scanner to see list of ingredients in your candle; highlight the toxic chemicals
* Can make it similar to Instagram - follow friends, see their stats, etc. to encourage community-building and consistent use

### 2. Screen Archetypes

* Login Screen
   * User can log in
* Stats Screen
   * User can view stats
* Candle burning logging screen
    * User can log hours of burning and set status to environmentally-friendly candle vs. toxic candle
* **Optionals:**
* Timer Screen
    * User can set a timer
* Fake Candle Screen
    * User can see a video of a fake candle
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
<img width="666" alt="wireframeSketches" src="https://user-images.githubusercontent.com/68299385/124969034-13ec0980-dfdb-11eb-9e21-24740ee22859.png">

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 

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
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]

Cocktail API
https://www.thecocktaildb.com/api.php
| HTTP Verb | Endpoint | Description |
| -------- | -------- | -------- |
| GET     | /strDrink     | name of drink     |
| GET     | /strDrinkThumb     | image of cocktail     |
