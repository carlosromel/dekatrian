# Calendário Dekatrian
Projeto que fornece um exemplo funcional do calendário Dekatrian.

## Obtendo, testando e construindo o projeto
1. Obtendo...
```shell
git clone https://github.com/carlosromel/dekatrian.git
```

1. Testando e construíndo
```shell
cd dekatrian
mvn clean package
```

## Rodando o projeto localmente
```shell
mvn spring-boot:run
```
Abra o projeto em [Calendário Dekatrian](http://localhost:5000)

## Projeto hospedado no Heroku
```shell
heroku login
heroku apps:create sua_versao_do_projeto
heroku git:remote -a sua_versao_do_projeto
git push heroku master
heroku open
```

## Projeto como dependência
1. Acrescente ao seu projeto, a dependência:
```XML
<dependency>
    <groupId>br.eti.romel.lounge</groupId>
    <artifactId>dekatrian</artifactId>
    <version>1.0-SNAPSHOT</version>
</depencency>
```

1. Convertendo uma data gregoriana para dekatriana
```java
DekatrianCalendar dekatrian = new DekatrianCalendar(new GregorianCalendar());
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
System.out.printf("Data atual (gregoriano): %s%n", sdf.format(new GregorianCalendar().getTime()));
System.out.printf("Data atual.(dekatrian).: %s%n", dekatrian.toString());
System.out.printf("Mês anterior...........: %s%n", dekatrian.previousMonth().toString());
System.out.printf("Próximo mês............: %s%n", dekatrian.nextMonth().toString());
System.out.printf("Convertido.............: %s%n", sdf.format(dekatrian.getTime()));
```

Espero que seja útil.
