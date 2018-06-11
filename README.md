# CurrencyExchange

![My image](https://github.com/kaustubhkp/CurrencyExchange-master/blob/master/screen-shot/currency_img1.jpg)

# Quick Start:
- Clone project<br>
- Build and Run<br>

# External Libraries:
- RxJava <br>
- Retrofit <br>
- Dagger <br>
- Butter knife <br>

# Architecture:<br>
This project uses MVP pattern. It allowed for rapid iteration and it would seamlessly support any unit or instrumented tests down the line due to the separation of concerns. The project is further organised into different modules based on functionality and their usage and classification is explained below. These sections also feature any improvements that could be done to these respective modules. 

<br>

# App:
Initialises Dagger’s parent component to make them available for the entire lifecycle of the app.<br>

Improvements: Implementing LeakCanary to detect memory leaks

# Dagger:<br>
Dependency Injection is done via Dagger. AppComponent serves as the parent component and exposes the Retrofit Service for its dependent components. All the components provided here are scoped to be a Singleton and the dependent components (implemented for each screen) have scopes restricting them to their respective modules, enabling modularity of the codebase. <br>

Improvements: Using Dagger-Android to make the activities more agnostic about their injections. This would also reduce the dependency of Activity having to provide the view to initialise the dependent module. <br>

# Home:
<br>
Currently, the only screen of the app. It uses a Recycler view to display the results fetched from the API. Picasso is used to display the icons for respective country flags, offloading the memory and cache management to the library. A HomeComponent is constructed via Dagger and serves as the dependent component and is responsible for injecting presenter, view and other required components for this module. The presenter chooses whether to subscribe to the observable returned by API based on network connectivity and manages UI state during and after the connection.  <br>
Improvements: Creating a repository, so the presenter can be agnostic about the source of data. The repository will also manage storing, caching and retrieval of data, offloading those functions off the presenter. Designing a better UI with colour changes (Using Palette support library).<br>

# Model:
<br>
POJOs representing the response model from the API.<br>

# Net:
<br>
Interface for Retrofit to communicate with the API. The Retrofit instance provided by Dagger uses OkHttp’s and uses RxJava adapter to return the results in form of an Observable. <br>
Improvements: Implementing support for pagination and refined results based on differing parameters<br>

# Lint:
<br>
Lint ran on the code and removed most of the error's and warnings <br>

# General Improvements:
<br>

- Tests. Tests. Tests. Unit tests for the business logic and instrumentation tests for the UI <br>

- Support for landscape/tablet UI <br>

- Instead of showing data from API directly put it into database via Room and then show it from database to UI so it will be single source of truth<br>

- Currently all country flag icons added into drawable which needs to add for all resolution or use standard library to show all flag icons using picasso



