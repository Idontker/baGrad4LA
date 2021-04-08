# baGrad4LA

## Contributors: 
* Athanassios Karol Bakas
* Paul Meyer

## Config Structure
### Fach:
"#" fachname ects_ba ects_total

### Modul:
modulname : ects : weight : modifier_for_subjects : modifiers_rest
* modulname = Name des Moduls
* ects = ECTS des Moduls
* modifier_for_subjects = { \[+|-\] \[fachnamen\] }* 
  * +Mathematik = Modul wird *nur* angezeigt, falls ein Nebenfach Mathematik ist
  * -Mathematik = Modul wird *nicht* angezeigt, falls ein Nebenfach Mathematik ist
* modifiers_rest = \[BA\]\[!\]
  * BA = Modul kann oder muss bei der BA-Note eingebracht werden
  * ! = Modul *muss* bei der BA-Note eingebracht werden
