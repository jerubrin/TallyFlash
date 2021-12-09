# TallyFlash
 _Application that can replace your TallyLight system (almost always)_  
## Short instructions for using the app:
1. Connect to vMix using your local __IP-address__ and __port__ and then click the `Connect` button  
You can find them at vMix `Settings` -> `Web Controller` _(look at the pictures down below)_  

![Connection settings](/img/connect_to_vmix.png?raw=true)  

2. Choose your __camera__ scene from the list
3. Start working  

![Start working with TallyLight](/img/3_steps.png?raw=true)  

There are 3 scenes for notification, and 3 different flashlight reactions _(off, on or blink)_  

![Status](/img/off_preview_active.png?raw=true)  

You can customize your screens (background color, status labels) and flashlight switching in settings  

![Settings](/img/settings.png?raw=true)  

## Application main diagram
This app provides data on 3 levels:
1. Presentation (UI) level - on this level users interact with app use fragments and service.
2. Domain level - there we have UseCases, they receive data from the retrofit, implement business logic, and send the result to UI level. And vice versa, it can be receive data from UI, convert it and send it up to data level.
3. Data level - there we have the retrofit repository (to receive remote data from vMix), and app string resources.  

![Main diagram](/img/diagram.png?raw=true)  

## Application detailed diagram
There you can see the application detailed diagram, and all objects, that can be using to send and receive data:  

![Detailed diagram](/img/detailed_diagram.png?raw=true)
![Entities](/img/all_entities.png?raw=true)
