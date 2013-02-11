
# Cygwin-Windows-Environment, Environment variable COMSPEC is set
ifneq ($(COMSPEC),)
AKSEN = C:\\cygwin\\usr\\local\\aksen-lib
AKSEN_CYG = /usr/local/aksen-lib


else
# other, mostly Linux
AKSEN = /usr/local/aksen-lib
endif

# ------------------------ No need to change anything after this line ----------------------------------------------

ifneq ($(COMSPEC),)
AKSEN_INC = $(AKSEN)\\include
AKSEN_REL = $(AKSEN_CYG)/stub/AkSen.rel
AKSEN_LST = $(AKSEN_CYG)/stub/AkSen.lst

else
# other, mostly Linux
AKSEN_INC = $(AKSEN)/include
AKSEN_REL = $(AKSEN)/stub/AkSen.rel
AKSEN_LST = $(AKSEN)/stub/AkSen.lst
endif

# for all environments
SDCC_LIB = large
CC = sdcc
C_FLAGS = -V -c --model-large --data-loc 0x18 --stack-auto -I $(AKSEN_INC) 
L_FLAGS = -V --model-large --data-loc 0x18 --stack-auto -L$(SDCC_LIB) AkSen.rel
