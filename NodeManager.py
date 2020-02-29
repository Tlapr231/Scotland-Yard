filename = "NodetextUpdater.txt"
j = 0
with open(filename) as file_object:
    lines = file_object.readlines()

    for line in lines:

        if (line.strip() == "i++;") :
            continue
        else :
            j += 1

        onX = True
        i = 20
        x = ""
        y = ""

        while(i < len(line)) :
            rep = line[i]
            #print(rep);
            if (rep != ',') :
                if (onX) :
                    x = x + rep
                else :
                    y = y + rep
            else :
                if (onX == False) :
                    break
                onX = False

            i = i + 1

#print(int(x))
#print(int(y))

        x = int(x) * 1.05
        y = int(y) * 0.75

        x = int(x)
        y = int(y)

#print(int(x))
#print(int(y))

        print("nodes[" + str(j) + "] = new Node(" + str(x) + ", " + str(y) + ", " + str(j) + ");")
