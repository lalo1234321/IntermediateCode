/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mini_C_Compiler;

import java.util.ArrayList;

/**
 *
 * @author David
 */
public class TypeCheck 
{
    boolean unique;
    boolean arithValidTypes = true;
    boolean validType = true;
    node nodo;
    MySymTable mySymTable;

    public TypeCheck() {
    }

    public TypeCheck(MySymTable mySymtable) {
        this.mySymTable = mySymtable;
    }

 
    //Para validar que las variables que se estan usando ya han sido declaradas.
    public void startCheck(int prof, node myAst)
    {
       
          
         for (int i = myAst.children.size() - 1; i >= 0; i--)
         {
            
             
             if (myAst.children.get(i).sym.equals("print")||myAst.children.get(i).sym.equals("==")||myAst.children.get(i).sym.equals("=/=")||myAst.children.get(i).sym.equals("<")||myAst.children.get(i).sym.equals(">")||myAst.children.get(i).sym.equals(">=")||myAst.children.get(i).sym.equals("<=")||myAst.children.get(i).sym.equals("forParams")||myAst.children.get(i).sym.equals("=")||myAst.children.get(i).sym.equals("+")||myAst.children.get(i).sym.equals("-")||myAst.children.get(i).sym.equals("*")||myAst.children.get(i).sym.equals("/")||myAst.children.get(i).sym.equals("callParam")) 
             {
                          //System.out.println("Soy un igual!");
                          for (int j = 0; j < myAst.children.get(i).children.size(); j++) 
                          {
                              //System.out.print("variable " + myAst.children.get(i).children.get(j).sym + " es ");
                              //System.out.println(myAst.children.get(i).children.get(j).variable);;
                              if (myAst.children.get(i).children.get(j).variable == true) 
                              {
                                  boolean declaredInScope = false;
                                  for (int k = 0; k < mySymTable.symtable.size(); k++) 
                                  {
                                      //Revisa que la variable exista en la tabla de s??mbolos y que la variable ha sido declarada en su mismo scope o en uno superior y que no sea una funci??n.
                                      if ((mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(j).sym)&&(!mySymTable.symtable.get(k).isFuncion()))&&((mySymTable.symtable.get(k).ambito.equals(myAst.children.get(i).children.get(j).nodeScope))||(myAst.children.get(i).children.get(j).nodeScope.contains(mySymTable.symtable.get(k).ambito))))
                                      {
                                         // System.out.println("Variable " + myAst.children.get(i).children.get(j).sym + " declarada");
                                         declaredInScope = true;
                                          
                                      }
                                     
                                  }
                                  if (declaredInScope == false) 
                                  {
                                       validType = false;
                                       System.out.println("Error en la l??nea:"+myAst.children.get(i).children.get(j).lin
                                       +" columna: "+myAst.children.get(i).children.get(j).col+", variable "+ myAst.children.get(i).children.get(j).sym + " no declarada");
                                  }
                                  //System.out.println("variables en igualdades: ");
                                  //System.out.println(myAst.children.get(i).children.get(j).sym);
                              }
                          }
                      }
             
             
             
             
                 
             if (!myAst.children.get(i).children.isEmpty()) 
             {
                            //child.writeTree(prof+=1);

                     startCheck(prof+=1, myAst.children.get(i));
                     prof-=1;
             }
             
         }       
    }
    
    //Para validar que los tipos sean los correctos.
    public void typeCheck(int prof, node myAst)
    {
        boolean validatedDeclaration = false;
         for (int i = 0; i < myAst.children.size() ; i++)
         //for (int i = myAst.children.size() - 1; i >= 0; i--)
         {
             //System.out.println(myAst.children.get(i).sym + " tipo: " + myAst.children.get(i).type);
              //para validar las declaraciones de variables.
//             if (myAst.children.get(i).sym.equals("declaracion")) 
//             {
//                 if (myAst.children.get(i).children.get(0).sym.equals(myAst.children.get(i).children.get(myAst.children.get(i).children.size()-1).type) != true) 
//                 {
//                     System.out.println("Error de tipo en la l??nea: " + myAst.children.get(i).lin  + ", declaraci??n inv??lida: " + myAst.children.get(i).children.get(myAst.children.get(i).children.size()-1).sym +
//                                            " de tipo: " + myAst.children.get(i).children.get(myAst.children.get(i).children.size()-1).type + " no se puede convertir a " + myAst.children.get(i).children.get(0).sym);
//                     myAst.children.get(i).children.get(0).sym = "error_tipo";
//                     myAst.children.get(i).children.get(0).type = "error_tipo";
//                     //AQUI
//                 } 
//             }
             
              //para validar los tipos de las variables usadas en los parametros de los bloques for.
             if (myAst.children.get(i).sym.equals("forParams")) 
             {
                
                 for (int j = 0; j < myAst.children.get(i).children.size(); j++) 
                 {
                     if (myAst.children.get(i).children.get(j).variable == true) //entra si el nodo contiene una variable.
                    {
                        for (int k = 0; k < mySymTable.symtable.size(); k++) 
                        {
                            //Entra si la variable fue declarada en su ??mbito y si no es una funci??n
                            if ((mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(j).sym))&&(myAst.children.get(i).children.get(j).nodeScope.contains(mySymTable.symtable.get(k).ambito))&&(!mySymTable.symtable.get(k).isFuncion()))
                            {
                                //entra si la variable es de tipo int.
                                if (mySymTable.symtable.get(k).tipo.equals("int")) 
                                {
                                   
                                }
                                else
                                {
                                    validType = false;
                                    System.out.println("Error de tipo en la l??nea: " + myAst.children.get(i).children.get(j).lin  + ", variable: " + myAst.children.get(i).children.get(j).sym +
                                            " de tipo: " + mySymTable.symtable.get(k).tipo + " no se puede convertir a int");
                                }
                            }
                        }
                    }
                 }
                 
             }
             
             //para validar los tipos de las comparaciones.(IFS y WHILES)
             if (!myAst.children.get(i).children.isEmpty()) //para validar que sea dentro de un if.
             {
                 
                  if ((myAst.children.get(i).sym.equals("<"))||(myAst.children.get(i).sym.equals(">"))||(myAst.children.get(i).sym.equals("<="))||(myAst.children.get(i).sym.equals(">="))||(myAst.children.get(i).sym.equals("=="))||(myAst.children.get(i).sym.equals("=/="))) 
                    {
                        
                        //para validar que valores bool solo puedan compararse con el operador "==" o "=/="
                        if (!(myAst.children.get(i).sym.equals("=="))&&((myAst.children.get(i).children.get(0).type == "bool")||(myAst.children.get(i).children.get(1).type == "bool"))) 
                        {
                            if (!myAst.children.get(i).sym.equals("=/=")) 
                            {
                                if (myAst.children.get(i).children.get(0).type == "bool") 
                                {
                                    validType = false;
                                    System.out.println("Error, tipo incompatible en la  l??nea: " + myAst.children.get(i).lin + " bool no puede ser convertido a dato num??rico, operador " + myAst.children.get(i).sym + " inv??lido.");
                                }
                                if (myAst.children.get(i).children.get(0).type == "char") 
                                {
                                    validType = false;
                                    System.out.println("Error, tipo incompatible en la  l??nea: " + myAst.children.get(i).lin + " char no puede ser convertido a dato num??rico, operador " + myAst.children.get(i).sym + " inv??lido.");
                                }
                                if (myAst.children.get(i).children.get(0).type == "string") 
                                {
                                    validType = false;
                                    System.out.println("Error, tipo incompatible en la  l??nea: " + myAst.children.get(i).lin + " string no puede ser convertido a dato num??rico, operador " + myAst.children.get(i).sym + " inv??lido.");
                                }
     
                                
                                
                            }
 
                        }
                        //Para validar que ambos operandos sean del mismo tipo
                        //ambos operandos son variables.
                       
                        if ((myAst.children.get(i).children.get(0).variable == true)&&(myAst.children.get(i).children.get(1).variable == true)) 
                        {
                            //System.out.println("Ambos operandos son variables");
                            for (int j = 0; j < mySymTable.symtable.size() ; j++) 
                            {
                                //Entra si la variable a la izquierda ya fue declarada en su mismo ??mbito y si no es una funci??n.
                                if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(0).sym))&&(myAst.children.get(i).children.get(0).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
                                {
                                     for (int k = 0; k < mySymTable.symtable.size() ; k++) 
                                    {
                                        //Entra si la variable de la derecha ya ha sido declarada en su mismo ??mbito y si no es una funci??n.
                                        if ((mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(1).sym))&&(myAst.children.get(i).children.get(0).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(k).isFuncion()))
                                        {
                                            //Entra si ambas variables son del mismo tipo.
                                            if ((mySymTable.symtable.get(j).tipo.equals(mySymTable.symtable.get(k).tipo))) 
                                            {
                                                //System.out.println("Tipos correctos en comparaci??n");
                                            }
                                            else
                                            {
                                                validType = false;
                                                System.out.println("Error de tipo en la l??nea: "+ myAst.children.get(i).lin +", comparando datos de tipos incompatibles: " 
                                                        + mySymTable.symtable.get(j).tipo + " con " + mySymTable.symtable.get(k).tipo);
                                            }

                                        }

                                    }
                                    
                                }
                               
                            }
                        }
                        //Entra si solo el operando de la izqueirda es variable.
                        else if ((myAst.children.get(i).children.get(0).variable == true)) 
                        {
                           
                            //System.out.println("Solo el primer operando es variable");
                            for (int j = 0; j < mySymTable.symtable.size(); j++) 
                            {
                                //Entra si la variable de la izqueirda fue declarada en el mismo ??mbito y si no es una funci??n.
                                if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(0).sym))&&(myAst.children.get(i).children.get(0).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
                                {
                                        //Entra si la variable de la izquierda es del mismo tipo que la constante del lado derecho de la comparaci??n.
                                        if (mySymTable.symtable.get(j).tipo.equals(myAst.children.get(i).children.get(1).type)) 
                                        {
                                            //System.out.println("Son tipos compatibles");
                                        }
                                        else
                                        {
                                            validType = false;
                                                System.out.println("Error de tipo en la l??nea: "+ myAst.children.get(i).lin +", comparando datos de tipos incompatibles: " 
                                                        + mySymTable.symtable.get(j).tipo + " con " + myAst.children.get(i).children.get(1).type);
                                        }
 
                                }
                            }
                        }
                        
                    }
                
             }
             //Para validar el parametro de la funci??n print().
             if (myAst.children.get(i).sym.equals("print")) 
             {
                 if (myAst.children.get(i).children.get(0).variable == true)
                 {
                     for (int j = 0; j < mySymTable.symtable.size(); j++) 
                     {
                         //La variable fue declarada en su mismo ??mbito y no es una funci??n.
                         if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(0).sym))&&(myAst.children.get(i).children.get(0).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
                         {
                            if (mySymTable.symtable.get(j).tipo != "string") 
                            {
                                validType = false;
                                System.out.println("Error de tipo en la l??nea: " + myAst.children.get(i).children.get(0).lin +
                                        ", variable: " + myAst.children.get(i).children.get(0).sym +" de tipo: "  +
                                        mySymTable.symtable.get(j).tipo + ", se esperaba string."
                                       );
                            }
                         }
                     }
                   
                 }
                 
             }
             
             //Para validar llamados de funciones
             ArrayList<String> my_tipo_funcion = new ArrayList<>();
             if (myAst.children.get(i).sym.equals("functionCall")) 
             {
                 //Para comprobar si la funci??n ya fue declarada.
                 for (int j = 0; j < mySymTable.symtable.size(); j++) 
                 {
                     //Entra si la funci??n fue declarada.
                     if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(0).sym))&&(mySymTable.symtable.get(j).isFuncion() == true)) 
                     {
                         my_tipo_funcion.add(mySymTable.symtable.get(j).tipo_funcion.get(0));//valor de retorno.
                        
                         //Itera dentro de los parametros de la funci??n en la llamada.
                         //Compara los par??metros en la llamada con los de la declaraci??n de la funci??n.
                         if (mySymTable.symtable.get(j).tipo_funcion.size() - myAst.children.get(i).children.get(1).children.size() == 1) 
                         {
                             for (int k = 0; k < myAst.children.get(i).children.get(1).children.size(); k++) 
                             {
                                 for (int l = 0; l < mySymTable.symtable.size(); l++) 
                                 {
                                     //Entra si la variable ha sido declarada en su ??mbito y no es una funci??n.
                                    if ((myAst.children.get(i).children.get(1).children.get(k).children.get(0).sym.equals(mySymTable.symtable.get(l).id)) && (mySymTable.symtable.get(l).isFuncion() != true) && (myAst.children.get(i).children.get(1).children.get(k).children.get(0).nodeScope.contains(mySymTable.symtable.get(l).ambito)))
                                    {
                                        my_tipo_funcion.add(mySymTable.symtable.get(l).tipo);
                                    }
                                    
                                 }
                                 
                             }
                             for (int k = 0; k < my_tipo_funcion.size(); k++) 
                             {
                                 if (my_tipo_funcion.get(k).equals(mySymTable.symtable.get(j).tipo_funcion.get(k))) 
                                 {
                                     //System.out.println("Tipos iguales! :D");
                                 }
                                 else
                                 {
                                     validType = false;
                                     System.out.println("Error de tipo en la l??nea: " + myAst.children.get(i).lin + " tipo " + my_tipo_funcion.get(k) +
                                             " incompatible, se esperaba " + mySymTable.symtable.get(j).tipo_funcion.get(k));
                                 }
                             }
                             
                         }
                         else
                         {
                             validType = false;
                             System.out.println("Error en la l??nea:" + myAst.children.get(i).lin + ", n??mero de par??metros incorrecto en el llamado a la funci??n: "+ mySymTable.symtable.get(j).id);
                         }
                     }
                 }
             }
             
             //Para validar las expresiones aritmeticas (solo tipos num??ricos iguales) y asignaciones.
             if ((myAst.children.get(i).sym.equals("="))&&myAst.children.get(i).children.size() > 0)
             {
                 //Entra si es una expresi??n aritmetica.
                 if ((myAst.children.get(i).children.get(1).sym.equals("+"))||(myAst.children.get(i).children.get(1).sym.equals("-"))||(myAst.children.get(i).children.get(1).sym.equals("*"))||(myAst.children.get(i).children.get(1).sym.equals("/")))
                 {
                     for (int j = 0; j < mySymTable.symtable.size(); j++) 
                     {
                         //Entra si la variable de la izquierda fue declarada en su ??mbito y no es una funci??n.
                         if ((myAst.children.get(i).children.get(0).sym.equals(mySymTable.symtable.get(j).id))&&(myAst.children.get(i).children.get(0).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
                         {
                             //Comprueba que la variable sea un int o un float.
                             if ((mySymTable.symtable.get(j).tipo.equals("int"))||(mySymTable.symtable.get(j).tipo.equals("float")))
                             {
                                 //Comprueba que todos los operadores del lado derecho sean del mismo tipo.
                                 arithmeticsCheck(0, myAst.children.get(i), mySymTable.symtable.get(j).tipo);
                                 if (arithValidTypes == true) 
                                 {
                                     //Expresi??n aritm??tica v??lida.
                                     //System.out.println("Expresi??n arith v??lida");
                                 }
                                 else
                                 {
                                     validType = false;
                                     //System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).lin + "variable " +);
                                 }
                             }
                             else
                             {
                                 validType = false;
                                 System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).lin+" tipo " +mySymTable.symtable.get(j).tipo+" no compatible, se esperaba dato num??rico para asignarle una expresi??n aritm??tica");
                             }
                         }
                     }
                 }
                 //Entra si es una asignaci??n.
                 else
                 {
                     //Comprueba que la variable del lado izquierdo haya sido declarada en su ??mbito y que no es una funci??n.
                     for (int j = 0; j < mySymTable.symtable.size(); j++)
                     {
                         if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(0).sym))&&(myAst.children.get(i).children.get(0).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
                         {
                             //Comprueba que la variable de la izquierda sea del mismo tipo que el dato de la derecha.
                             if (mySymTable.symtable.get(j).tipo.equals(myAst.children.get(i).children.get(1).type)) 
                             {
                                 //System.out.println("Tipos de asignaci??n correctos!!! ;D linea: " + myAst.children.get(i).lin);
                             }
                             else
                             {
                                 
                                 String tipoIzq = "izq";
                                 String tipoDer = "der";
                                 //System.out.println(myAst.children.get(i).children.get(1).sym);
                                 if (myAst.children.get(i).children.get(1).variable == true)//si el valor de la derecha es variable.
                                 {
                                     for (int k = 0; k < mySymTable.symtable.size(); k++) 
                                     {
                                         //variable a la derecha existe.
                                         //solo variables.
                                         if (!mySymTable.symtable.get(k).isFuncion()) 
                                         {
                                            if (mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(1).sym)) 
                                            {
                                                //tipo de la izquierda es el mismo que el de la derecha.
                                                tipoDer = mySymTable.symtable.get(k).tipo;

                                            }
                                            if (mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(0).sym)) 
                                            {
                                                //tipo de la izquierda es el mismo que el de la derecha.
                                                tipoIzq = mySymTable.symtable.get(k).tipo;

                                            }
                                         }
                                        
                                     }
                                     if (tipoDer.equals(tipoIzq)) 
                                     {
                                         //Tipos compatibles
                                     }
                                     else
                                     {
                                        
                                         validType = false;
                                         System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).lin+", asignaci??n inv??lida, "+tipoIzq 
                                                 + " con " + tipoDer + " incompatibles" );
                                     }
                                     
                                 }
                                 else//Si el valor de la derecha es constante.
                                 {
                                     validType = false;
                                     System.out.println("Error de tipo en la l??nea:" + myAst.children.get(i).lin + " tipos incompatibles, se esperaba " +
                                        mySymTable.symtable.get(j).tipo + " se encontro: " + myAst.children.get(i).children.get(1).type);
                                 }
                                 
                             }
                         }
                     }
                 }
                 
             }
             
             
             //Para validar los valores de retorno de las funciones.
             if (myAst.children.get(i).sym.equals("function")) 
             {
                 for (int j = 0; j < mySymTable.symtable.size(); j++) 
                     {
                         //Entra si la funci??n ya fue declarada y es funci??n
                         //System.out.println("AQUI: " + myAst.children.get(i).children.get(myAst.children.get(i).children.size() - 2).sym);
                         if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(1).sym))&&(mySymTable.symtable.get(j).isFuncion()))
                         {
                             boolean returnFound = false;
                             SymTableRow returnedVariable = new SymTableRow();
                             //recorre el bloque principal de la funci??n hasta encontrar un valor de retorno.
                             for (int k = 0; k < myAst.children.get(i).children.get(3).children.size(); k++) 
                             {
                                 if ((myAst.children.get(i).children.get(3).children.get(k).sym.equals("return"))&&(returnFound == false))
                                 {
                                     //SOLO FUNCIONA PARA TIPOS PRIMITIVOS, HACER PARA IDENTIFICADORES.
                                     //System.out.println("LLegue al return, tipo del return: ");
//                                     System.out.println(myAst.children.get(i).children.get(3).children.get(k).children.get(0).type);
//                                     System.out.println("Tipo de retorno de la funcion: ");
//                                     System.out.println(mySymTable.symtable.get(j).tipo_funcion.get(0));
                                     
                                     returnFound = true;
                                     //return inv??lido.
                                     //para retornos con variables.
                                     if (myAst.children.get(i).children.get(3).children.get(k).children.get(0).type.equals("iden")) 
                                     {
                                         boolean declaredReturnVariable = false;
                                         for (int l = 0; l < mySymTable.symtable.size(); l++) 
                                         {
                                             if (mySymTable.symtable.get(l).id.equals(myAst.children.get(i).children.get(3).children.get(k).children.get(0).sym)) 
                                             {
                                                 //comprueba que el ??mbito de la variable sea v??lido para la funci??n.
//                                                 System.out.println(mySymTable.symtable.get(l).id);
//                                                 System.out.println(mySymTable.symtable.get(l).ambito);
//                                                 System.out.println(mySymTable.symtable.get(j).id);
//                                                 System.out.println(mySymTable.symtable.get(j).ambito);
                                                 if ((mySymTable.symtable.get(l).ambito.equals(mySymTable.symtable.get(j).ambito + ".functions." + mySymTable.symtable.get(j).id))||(mySymTable.symtable.get(l).ambito.equals("null.program")))
                                                 {
                                                     declaredReturnVariable = true;
                                                     returnedVariable = mySymTable.symtable.get(l);
                                                 }
                                                
                                             }
                                             
                                         }
                                         if (declaredReturnVariable == false) 
                                         {
                                              validType = false;
                                              System.out.println("Error en la l??nea:"+myAst.children.get(i).children.get(3).children.get(k).children.get(0).lin
                                                     +", variable "+ myAst.children.get(i).children.get(3).children.get(k).children.get(0).sym + " no declarada");
                                         }
                                         else
                                         {
                                             //Comprueba que el tipo de la variable a retornar corresponde con el tipo de retorno de la funci??n.
//                                             System.out.println("funcion: " + mySymTable.symtable.get(j).id);
//                                             System.out.println("return type: " + mySymTable.symtable.get(j).tipo_funcion.get(0));
//                                             System.out.println("variable: " + returnedVariable.id);
//                                             System.out.println("variable tipo: " + returnedVariable.tipo);
                                             if (mySymTable.symtable.get(j).tipo_funcion.get(0).equals(returnedVariable.tipo)) 
                                             {
                                                // System.out.println("tipos v??lidos");
                                             }
                                             else
                                             {
                                                 System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).children.get(3).children.get(k).children.get(0).lin
                                                     +", tipos incompatibles, se encontro " + returnedVariable.tipo +  ", se esperaba "+ mySymTable.symtable.get(j).tipo_funcion.get(0) + " en valor de retorno.");
                                             }
                                         }
                                     }
                                     else
                                     {
                                          //para retornos de constantes.
                                         if (!myAst.children.get(i).children.get(3).children.get(k).children.get(0).type.equals(mySymTable.symtable.get(j).tipo_funcion.get(0))) 
                                         {
                                                    if (mySymTable.symtable.get(j).tipo_funcion.get(0).equals("void")) 
                                                    {
                                                        validType = false;
                                                        System.out.println("Error en la l??nea: " + (myAst.children.get(i).children.get(3).children.get(k).children.get(0).lin) + ",return inesperado en la funci??n void " + mySymTable.symtable.get(j).id);
                                                    }
                                                     validType = false;
                                                     System.out.println("Error en la l??nea: " + (myAst.children.get(i).children.get(3).children.get(k).children.get(0).lin) + ", Se esperaba " + mySymTable.symtable.get(j).tipo_funcion.get(0) + " en valor de retorno");
                                         }
                                              // System.out.println(myAst.children.get(i).children.get(k).children.get(0).type);
                                      }
                                     }
                                     
                                    
                                }
                             
                         }
                     }
                 //Para comprobar que la variable fue declarada solo si la funci??n no es void.
//                 if (myAst.children.get(i).children.get(myAst.children.get(i).children.size()-2).sym.endsWith("void")) 
//                 {
//                     if ((myAst.children.get(i).children.get(0).sym.equals("void"))) 
//                     {
//                         //System.out.println("Es void y no tiene return");
//                     }
//                     else
//                     {
//                         validType = false;
//                         System.out.println("Error en la l??nea: " + (myAst.children.get(i).children.get(0).lin) + ", Se esperaba return");
//                     }
//                 }
//                 else
//                 {
//                     boolean validReturn = false;
//                     String funcType = "";
//                     //Para declarar que la variable de retorno ya ha sido declarada.
//                     for (int j = 0; j < mySymTable.symtable.size(); j++) 
//                     {
//                         //Entra si la variable ya fue declarada en el mismo ??mbito y no es funci??n
//                         //System.out.println("AQUI: " + myAst.children.get(i).children.get(myAst.children.get(i).children.size() - 2).sym);
//                         if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).children.get(myAst.children.get(i).children.size() - 2).sym))&&(myAst.children.get(i).children.get(2).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
//                         {
//                             
//                             //System.out.println("entre!!!!!!");
//                             validReturn = true;
//                             funcType = mySymTable.symtable.get(j).tipo;
//                             
//                             
//                         }
//                         else
//                         {
//                             
//                            
//                         }
//                     }
//                     if (validReturn == false) 
//                     {
//                         validType = false;
//                          System.out.println("Error en la l??nea: " + (myAst.children.get(i).children.get(0).lin) + ", variable " +
//                                     myAst.children.get(i).children.get(myAst.children.get(i).children.size() - 2).sym + " no declarada");
//                         
//                     }
//                     else
//                     {
//                         //Entra si la variable es del mismo tipo que el tipo de retorno de la funci??n.
//                         if (funcType.equals(myAst.children.get(i).children.get(0).sym)) 
//                             {
//                                 //System.out.println("Tipos v??lidos!!!");
//                             }
//                         else
//                            {
//                                if (myAst.children.get(i).children.get(0).sym.equals("void")) 
//                                {
//                                    validType = false;
//                                    System.out.println("Error en la l??nea: " + (myAst.children.get(i).children.get(0).lin) + " return en funci??n void");
//                                }
//                                else
//                                {
//                                    validType = false;
//                                    System.out.println("Error en la l??nea: " + (myAst.children.get(i).children.get(0).lin) + ", variable " +
//                                     myAst.children.get(i).children.get(myAst.children.get(i).children.size() - 2).sym + " de tipo " +
//                                        funcType + ", se esperaba " + myAst.children.get(i).children.get(0).sym +  " en valor de retorno");
//                                }
//                                
//                            }
//                     }
//                     //System.out.println("No es funci??n void!");
//                 }
                 //System.out.println(myAst.children.get(i).children.get(myAst.children.get(i).children.size()-2).sym);
             }
             
             if (!myAst.children.get(i).children.isEmpty()) 
             {
                 typeCheck(prof+=1, myAst.children.get(i));
                 prof-=1;
             }
             
         }       
    }
public void arithmeticsCheck(int prof, node myAst, String arithType)
{
    for (int i = 0; i < myAst.children.size(); i++) 
    {
        //System.out.println(myAst.children.get(i).sym);
        //Para comprobar que los tipos de las constantes son iguales.
        if (myAst.children.get(i).type != null) 
        {
            if (!myAst.children.get(i).type.equals(arithType)) 
            {
                arithValidTypes = false;
                System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).lin + ", constante " + 
                        myAst.children.get(i).sym + " de tipo " + myAst.children.get(i).type
                + " incompatible, se esperaba " + arithType + " en expresi??n aritm??tica");
            }
        }
        //Para comprobar que los tipos de las variables son iguales.
        for (int j = 0; j < mySymTable.symtable.size(); j++) 
        {
            //Para comprobar que la variable fue declarada en su ??mbito y que no es una funci??n.
            myAst.children.get(i).nodeScope = myAst.children.get(i).nodeScope.replace(".=", "");
            if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).sym))&&(myAst.children.get(i).nodeScope.contains(mySymTable.symtable.get(j).ambito))&&(!mySymTable.symtable.get(j).isFuncion()))
            {
               /* if ((myAst.children.get(i).nodeScope.equals(mySymTable.symtable.get(j).ambito))) 
                {
                    */if (!mySymTable.symtable.get(j).tipo.equals(arithType)) 
                      {
                            arithValidTypes = false;
                             System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).lin + ", variable " + 
                                myAst.children.get(i).sym + " de tipo " + mySymTable.symtable.get(j).tipo
                        + " incompatible, se esperaba " + arithType + " en expresi??n aritm??tica");
                      }
               /* }
                else
                {
                    if (myAst.children.get(i).nodeScope.contains(mySymTable.symtable.get(j).ambito)) 
                    {
                        if (!mySymTable.symtable.get(j).tipo.endsWith(arithType)) 
                        {
                            arithValidTypes = false;
                             System.out.println("Error de tipo en la l??nea:"+myAst.children.get(i).lin + ", variable " + 
                                myAst.children.get(i).sym + " de tipo " + mySymTable.symtable.get(j).tipo
                        + " incompatible, se esperaba " + arithType);
                        }
                    }
 
                }*/
                
            }
         
        }
        
        if (!myAst.children.get(i).children.isEmpty()) 
             {
                 arithmeticsCheck(prof+=1, myAst.children.get(i), arithType);
                 prof-=1;
             }
        
    }
    
}
    
}
