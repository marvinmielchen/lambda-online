//example code for the editor
export const text =
`

//Im folgenden wird eine einfache Bool'sche Logik implementiert

//da es in Lambo keine Literale gibt, müssen wir diese selbst definieren
def true(t f){
    t
}

def false(t f){
    f
}

//und die Bool'schen Operatoren
def and(a b){
    a b false
}

def or(a b){
    a true b
}

def not(a){
    a false true
}

//jetzt da wir unsere Literale und Operatoren haben, können wir diese verwenden
def result {and true false}


`