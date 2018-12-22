
# coding: utf-8

# In[1]:


import numpy as np
import tensorflow as tf
import keras.backend as K
from keras.models import Sequential
from keras.layers import Dense
from keras.activations import sigmoid
from keras.activations import relu
from keras.activations import tanh
from keras.losses import MSE
from keras.losses import mean_squared_error
#from keras.losses import binary_crossentropy
from keras.optimizers import SGD
from keras.metrics import binary_accuracy

from tensorflow.python.framework.graph_util import convert_variables_to_constants


# In[2]:


sess=tf.Session()
K.set_session(sess)


# In[3]:


training_data = np.array([[0,0], [0,1], [1,0], [1,1]])
target_data   = np.array([  [0],   [1],   [1],   [0]])


# In[4]:


model = Sequential()
model.add(Dense(8, input_dim=2, activation='sigmoid'))
model.add(Dense(1, activation='sigmoid'))
model.compile(loss='mean_squared_error', optimizer=SGD(lr=1), metrics=['accuracy'])


# In[5]:


from IPython.display import SVG
from keras.utils.vis_utils import model_to_dot

SVG(model_to_dot(model, show_shapes=True, rankdir='LR').create(prog='dot', format='svg'))


# In[6]:


epochs = 2000
model.fit(training_data, target_data, epochs=epochs)


# In[7]:


print "loss:", model.evaluate(x=training_data, y=target_data, verbose=0)
print ""
print model.predict(training_data)
print ""
print model.predict(training_data).round()


# ## TensorFlow

# In[8]:


### save as TensorFlow and TensorFlow Lite
if False:
    with sess.graph.as_default():

        print model.inputs[0].name
        print model.outputs[-1].name

        freeze_var_names = list(set(v.op.name for v in tf.global_variables()))
        output_names = [out.op.name for out in model.outputs] + [v.op.name for v in tf.global_variables()]
        input_graph_def = sess.graph.as_graph_def()

        # clear devices
        for node in input_graph_def.node:
            node.device = ""

        from tensorflow.python.framework.graph_util import convert_variables_to_constants
        frozen_graph = convert_variables_to_constants(sess, input_graph_def,
                                                          output_names, freeze_var_names)

        tf.train.write_graph(frozen_graph, "models", "xor.pb", as_text=False)
        print "xor.pb written"


# In[9]:


### freeze model and save as Tensorflow 

freeze_var_names = list(set(v.op.name for v in tf.global_variables()))
output_names = [model.outputs[0].name.split(":")[0]]

frozen_graph = convert_variables_to_constants(sess,
                                              sess.graph.as_graph_def(),
                                              output_names,
                                              freeze_var_names)

tf.train.write_graph(frozen_graph, "models", "xor.pb", as_text=False)
print "xor.pb written"


# ## TensorFlow Lite

# In[10]:


## use another graph and import the frozen graph 

fgraph = tf.Graph()
with fgraph.as_default():
    tf.import_graph_def(frozen_graph, name="")
    fsess = tf.Session(graph=fgraph)

    toco = tf.contrib.lite.TocoConverter
    converter = toco.from_session(fsess, model.inputs, model.outputs)
    tflite_model = converter.convert()
    open("models/xor.tflite", "wb").write(tflite_model)
    
    print "xor.tflite written"


# ## CoreML

# In[11]:


import tfcoreml

coreml_model = tfcoreml.convert(
        tf_model_path="./models/xor.pb",
        mlmodel_path="./models/xor.mlmodel",
        input_name_shape_dict={model.inputs[0].name:[1,2]},
        output_feature_names=[model.outputs[0].name])

print "---"
print coreml_model.input_description
print coreml_model.get_spec
#spec = coreml_model.get_spec
#print spec.description
print coreml_model.predict({model.inputs[0].name.replace(':', '__').replace('/', '_Q_'): [0.0, 0.0]})

coreml_model.author = 'AUTHOR'
coreml_model.save('models/xor.mlmodel')

#print model.inputs[0].name.replace(':', '__').replace('/', '__')
#print model.outputs[0].name.replace(':', '__').replace('/', '__')


# ### CoreML Tools Keras

# In[12]:


### save as CoreML with Keras converter

from coremltools.converters.keras import convert

coreml = convert(model, 
                 input_names = 'input', 
                 output_names = ['result'])
coreml.input_description['input'] = '2-dim input array'
coreml.output_description['result'] = 'XOR result'
coreml.short_description = 'XOR model'
coreml.save("models/xor.mlmodel")

coreml.predict({'input': [0.0, 0.0]})

