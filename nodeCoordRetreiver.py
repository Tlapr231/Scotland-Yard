filename = 'nodeCoordData.txt'

for i in range(1,200) :
    x = str(input("Node " + str(i) + " x : "))
    y = str(input("Node " + str(i) + " y : "))

    with open(filename, 'a') as file_object:
        file_object.write("nodes[i] = new Node(" + x + ", " + y + ", i);\n")
