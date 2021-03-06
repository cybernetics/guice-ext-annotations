* Fix class generation for dynamic class loaders cases (required, for example, for playframework dev mode):
    - dynamic classes are checked now against class loader of original type
    - different classes will be generated for the same type from different class loaders
* Make class generator thread safe (fixes concurrent provider calls issue) 

### 1.1.1 (2015-01-06)
* Fix recognition of javax.inject.Inject annotation during class generation
* Generated class now contains valid generics signature (required for Provided parameters)
* Add DynamicSingletonProvider: shortcut for DynamicClassProvider to produce singleton bean (without need for additional annotation)
* Fix type post processor abstract class recognition

### 1.1.0 (2014-12-14)
* Update guice 3.0 -> 4.0-beta5
* Add DynamicClassGenerator: interfaces and abstract classes may be used as guice beans with complete aop support
* Add DynamicClassProvider for abstract classes and interfaces to use in @ProvidedBy (JIT resolution will trigger dynamic class generation )

### 1.0.1 (2014-08-16)
* Fix pmd/checkstyle warnings

### 1.0.0 (2014-07-02)
* Initial release