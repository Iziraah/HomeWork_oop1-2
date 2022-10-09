// package ru.gb;

import java.util.ArrayList;

public class parents {
  public static void main(String[] args) {
    Person varvara = new Person("Varvara", Sex.female);
    Person vasiliy = new Person("Vasiliy", Sex.male);
    Person vladimir = new Person("Vladimir", Sex.male);
    Person nadezhda = new Person("Nadezhda", Sex.female);
    Person ilya = new Person("Ilya", Sex.male);
    Person aleksandr = new Person("Aleksandr", Sex.male);
    Person kseniya = new Person("Kseniya", Sex.female);
    Person mariia = new Person("Mariia", Sex.female);
    Person timofei = new Person("Timofei", Sex.male);
    Person aleksandra = new Person("Aleksandra", Sex.female);
    Person petr = new Person("Petr", Sex.male);   
    Person galina = new Person("Galina", Sex.female);
    Person elena = new Person("Elena", Sex.female);
    Person sergey = new Person("Sergey", Sex.male);

    GeoTree gt = new GeoTree();
    gt.append(varvara, vasiliy);
    gt.append(vasiliy, nadezhda);
    gt.append(galina, nadezhda);
    gt.append(galina, vladimir);
    gt.append(vasiliy, vladimir);
    gt.append(vladimir, ilya);
    gt.append(elena, ilya);
    gt.append(vladimir, aleksandr);
    gt.append(elena, aleksandr);
    gt.append(nadezhda, kseniya);
    gt.append(nadezhda, mariia);
    gt.append(sergey, mariia);
    gt.append(sergey, kseniya);
    gt.append(kseniya, timofei);
    gt.append(kseniya, aleksandra);
    gt.append(kseniya, petr);
    gt.appendSpouse(vasiliy, galina);
    gt.appendSpouse(vladimir, elena);
    gt.appendSpouse(nadezhda, sergey);

    System.out.println(new ResearchParents(gt).spend(ilya, Relationship.children));
    System.out.println(new ResearchPapaMama(gt).spend(ilya, Relationship.children, Sex.male));
    System.out.println(new ResearchBro(gt).spend(timofei));
  }
}

enum Relationship {
  parent,
  children,
  spouse
}

enum Sex {
  male,
  female
}


class Person {
  private String fullName;
  Sex sex;

  public String getFullName() {
    return fullName;
  }

  public Person(String fullName, Sex sex) {
    this.fullName = fullName;
    this.sex = sex;
  }

  @Override
  public String toString() {
    return String.format("(%s)", this.fullName);
  }
}

class Node {
  public Node(Person p1, Relationship re, Person p2) {
    this.p1 = p1;
    this.re = re;
    this.p2 = p2;
  }

  Person p1;
  Relationship re;
  Person p2;

  @Override
  public String toString() {
    return String.format("<%s %s %s>", p1, re, p2);
  }
}

class GeoTree {
  private ArrayList<Node> tree = new ArrayList<>();

  public ArrayList<Node> getTree() {
    return tree;
  }

  public void append(Person parent, Person children) {
    tree.add(new Node(parent, Relationship.parent, children));
    tree.add(new Node(children, Relationship.children, parent));
  }
  public void appendSpouse(Person parent, Person spouse){
    tree.add(new Node(parent, Relationship.spouse, spouse));
}
}

class ResearchParents {
  ArrayList<Node> tree;

  public ResearchParents(GeoTree geoTree) {
    tree = geoTree.getTree();
  }

  public ArrayList<Person> spend(Person p, Relationship re) {
    ArrayList<Person> result = new ArrayList<>();
    for (Node t : tree) {
      if (t.p1.getFullName() == p.getFullName() && t.re == re) {
        result.add(t.p2);
      }
    }
    return result;
  }
}

class ResearchPapaMama {
  ArrayList<Node> tree;

  public ResearchPapaMama(GeoTree geoTree) {
    tree = geoTree.getTree();
  }

  public ArrayList<Person> spend(Person p, Relationship re, Sex s) {
    ArrayList<Person> result = new ArrayList<>();
    for (Node t : tree) {
      if (t.p1.getFullName() == p.getFullName() && t.re == re) {
        if(t.p2.sex == s){
        result.add(t.p2);
      }}
    }
    return result;
}}

class ResearchBro {
  ArrayList<Node> tree;

  public ResearchBro(GeoTree geoTree) {
    tree = geoTree.getTree();
  }

  public ArrayList<Person> spend(Person p) {
    Relationship reCh = Relationship.children;
    Relationship reP = Relationship.parent;
    Sex x = Sex.female;
    Person p3 = new Person(null, x);
    ArrayList<Person> result = new ArrayList<>();
    for (Node t : tree) {
      if (t.p1.getFullName() == p.getFullName() && t.re == reCh) {
        if(t.p2.sex == x){
          p3 = t.p2;}}}
    for (Node t : tree) {      
      if (t.p1.getFullName() == p3.getFullName() && t.re == reP) {
        if(t.p2.getFullName() != p.getFullName()){
        result.add(t.p2);
      }} 
    }
    return result;
}
}

class Printer {
  // ...
}