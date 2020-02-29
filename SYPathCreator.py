def newPath(node) :
    filename = 'nodeData.txt'

    with open(filename, 'a') as file_object:
        file_object.write("\n//Node " + node + "\n")

        taxi = input("Taxi : ")

        while(taxi != "") :
            file_object.write("\nnodes[" + node + "].addPath(new Path(nodes[" + node + "], nodes[" + taxi + "], 1));")
            taxi = input("Taxi : ")

        bus = input("Bus : ")

        while(bus != "") :
            file_object.write("\nnodes[" + node + "].addPath(new Path(nodes[" + node + "], nodes[" + bus + "], 2));")
            bus = input("Bus : ")

        train = input("Train : ")

        while(train != "") :
            file_object.write("\nnodes[" + node + "].addPath(new Path(nodes[" + node + "], nodes[" + train + "], 3));")
            train = input("Train : ")



for i in range(1,200) :
    print("\nNode " + str(i))
    newPath(str(i))
