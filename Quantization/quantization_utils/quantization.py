import coremltools
from coremltools.models.neural_network.quantization_utils import *

path = '../InceptionV3Quantization/'

model = coremltools.models.MLModel(path + 'Inceptionv3.mlmodel')
lin_quant_model_16l = quantize_weights(model, 16, "linear")
lin_quant_model_16l.save(path + 'Inceptionv3Q16linear.mlmodel')

#lin_quant_model_16k = quantize_weights(model, 16, "kmeans")
#lin_quant_model_16k.save(path + 'Inceptionv3Q16kmeans.mlmodel')

lin_quant_model_8l = quantize_weights(model, 8, "linear")
lin_quant_model_8l.save(path + 'Inceptionv3Q8linear.mlmodel')

# this takes some time
#lin_quant_model_4l = quantize_weights(model, 4, "linear")
#lin_quant_model_4l.save(path + 'Inceptionv3Q4linear.mlmodel')
