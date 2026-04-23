cmd
chcp 65001
java -Dfile.encoding=UTF-8 -cp bin ticketmanagement.App src/data/collection.json


javadoc:
javadoc -d ../docs -encoding UTF-8 -docencoding UTF-8 ticketmanagement/*.java