from chain import Chain

chain = Chain(20)

#i = 0

for i in range(5):
    #data = input("Add something to the chain: ")
    data = str(i)
    chain.add_to_pool(data)
    chain.mine()